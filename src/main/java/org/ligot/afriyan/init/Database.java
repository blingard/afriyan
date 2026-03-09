package org.ligot.afriyan.init;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.echo.dto.Feature;
import org.ligot.afriyan.echo.dto.GeoJson;
import org.ligot.afriyan.echo.dto.ReverseGeocodingResponse;
import org.ligot.afriyan.echo.entities.*;
import org.ligot.afriyan.echo.repo.*;
import org.ligot.afriyan.echo.service.AlertRiskTypeService;
import org.ligot.afriyan.echo.service.GeoJsonService;
import org.ligot.afriyan.entities.*;
import org.ligot.afriyan.repository.IGroupesRepository;
import org.ligot.afriyan.service.ICategories;
import org.ligot.afriyan.service.IMenus;
import org.ligot.afriyan.service.IParametres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class Database implements CommandLineRunner {
        private final PaysRepo paysRepo;
        private final LocalityRepo localityRepo;
        private final RegionsRepo regionsRepo;
        private final DepartementsRepo departementsRepo;
        private final CommuneRepo communeRepo;
        private final IMenus menus;
        private final ICategories categories;
        private final AlertRiskTypeService alertRiskTypeService;
        private final IParametres iParametres;
        private final IGroupesRepository groupesRepository;

        @Autowired
    public Database(PaysRepo paysRepo, LocalityRepo localityRepo, RegionsRepo regionsRepo, DepartementsRepo departementsRepo, CommuneRepo communeRepo, IMenus menus, ICategories categories, AlertRiskTypeService alertRiskTypeService, IParametres iParametres, IGroupesRepository groupesRepository) {
        this.paysRepo = paysRepo;
        this.localityRepo = localityRepo;
        this.regionsRepo = regionsRepo;
        this.departementsRepo = departementsRepo;
        this.communeRepo = communeRepo;
            this.menus = menus;
            this.categories = categories;
            this.alertRiskTypeService = alertRiskTypeService;
            this.iParametres = iParametres;
            this.groupesRepository = groupesRepository;
        }



/*        public Database(CategorieEntitiesRepo repo, IGroupesRepository serviceGroupe, IRolesRepository serviceRole,
                        IAdministrateur serviceAdmin, IUtilisateur iUtilisateur, IRegionsRepository iRegionsRepository,
                        IDepartementsRepository iDepartementsRepository,
                        IArrondissementsRepository iArrondissementsRepository) {
                this.repo = repo;
                this.serviceGroupe = serviceGroupe;
                this.serviceRole = serviceRole;
                this.serviceAdmin = serviceAdmin;
                this.iUtilisateur = iUtilisateur;
                this.iRegionsRepository = iRegionsRepository;
                this.iDepartementsRepository = iDepartementsRepository;
                this.iArrondissementsRepository = iArrondissementsRepository;
        }*/

        /*private Departements saveDepWithArr(Map<String, Object> dep) {
                String[] arrString = (String[]) dep.get("arr");
                Set<Arrondissements> arrondissements = Arrays.stream(arrString)
                                .map(s -> iArrondissementsRepository.save(new Arrondissements(null, s)))
                                .collect(Collectors.toSet());
                return iDepartementsRepository.save(new Departements(null, dep.get("dep").toString(),
                                dep.get("cl").toString(), arrondissements));
        }*/

        public Region saveRegion(String name){
                final Pays pays = saveCountry();
                Region regions = new Region(null, name, pays);
            return regionsRepo.findRegionByName(regions.getName()).orElseGet(() -> regionsRepo.save(regions));
        }

        public Pays saveCountry(){
                Pays pays = new Pays(null, "Cameroun", "CMR");
                Optional<Pays> paysOp = paysRepo.findPaysByAbbr(pays.getAbbr());
            return paysOp.orElseGet(() -> paysRepo.save(pays));
        }

        @Transactional
    public Set<Localities> getAll(String id) {
        Set<Localities> localities = new HashSet<>();

        try {
            List<String> regionsList = new ArrayList<>(Arrays.asList(id/*
                    ,"Sud",
                    "Sud-Ouest"*/));
            Region region = saveRegion(id);
            GeoJson geoJson = new GeoJsonService().readGeoJsonFile(id);
            List<Feature> features = geoJson.getFeatures();
            System.err.println("Region: "+id+" -> "+features.size());

            Map<Departement, Map<Communes, List<Localities>>> map = new ConcurrentHashMap<>();
            for (int i = 0; i < features.size(); i++) {
                System.err.println((i+1)+"->");
                Feature feature = features.get(i);
                List<Double> latLong = List.of(
                        feature.getGeometry().getCoordinates().get(1),
                        feature.getGeometry().getCoordinates().get(0)
                );
                ReverseGeocodingResponse geoResponse = callReverseGeocoding(latLong.get(0), latLong.get(1));
                if (geoResponse == null || geoResponse.getAddress() == null) continue;
                String town = Optional.ofNullable(geoResponse.getAddress().getTown())
                        .orElse(geoResponse.getAddress().getCity());
                if(town==null)
                    town = "Commune"+i+"("+geoResponse.getAddress().getCounty()+")";
                System.err.println(town);
                if(town.length()>=25)
                    town = town.substring(20);


                Departement departement = new Departement(null, geoResponse.getAddress().getCounty(), region);
                Communes commune = new Communes(null, town, departement, null, null);
                String name = Optional.ofNullable(feature.getProperties().get("name"))
                        .map(Object::toString)
                        .orElse("Village " + i);

                Localities locality = new Localities(
                            null, name, latLong.get(0).toString(), latLong.get(1).toString(),
                            LocalityType.get(String.valueOf(feature.getProperties().get("place"))), commune
                );

                localities.add(locality);
                map.computeIfAbsent(departement, d -> new ConcurrentHashMap<>())
                            .computeIfAbsent(commune, c -> Collections.synchronizedList(new ArrayList<>()))
                            .add(locality);
                }

                // Enregistrement en base
                for (var entry : map.entrySet()) {
                    Departement dep = departementsRepo.findDepartementsByName(entry.getKey().getName())
                            .orElseGet(() -> departementsRepo.save(entry.getKey()));

                    for (var communeEntry : entry.getValue().entrySet()) {
                        Communes commune = communeEntry.getKey();
                        commune.setDepartement(dep);
                        final Communes communes = commune;
                        commune = communeRepo.findCommunesByName(commune.getName()).orElseGet(() -> communeRepo.save(communes));
                        for (Localities loc : communeEntry.getValue()) {
                            loc.setCommune(commune);
                            System.err.println("Commune: -> "+commune.getName());
                            System.err.println("    Locality: -> "+loc.getName());
                            localityRepo.findByNameAndCommune_Id(loc.getName(), commune.getId()).orElseGet(() -> localityRepo.save(loc));
                        }
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return localities;
    }

    private ReverseGeocodingResponse callReverseGeocoding(Double lat, Double lon) {
        try {
            HttpResponse<ReverseGeocodingResponse> response = Unirest
                    .get("https://nominatim.openstreetmap.org/reverse")
                    .queryString("format", "json")
                    .queryString("lat", lat)
                    .queryString("lon", lon)
                    .queryString("zoom", "10")
                    .queryString("addressdetails", "1")
                    .header("accept", "application/json")
                    .header("User-Agent", "YourAppNameHere")
                    .asObject(new GenericType<>() {});

            return response.getBody();
        } catch (Exception e) {
            System.err.println("Erreur lors du reverse geocoding pour lat=" + lat + ", lon=" + lon);
            return null;
        }
    }



        /*public Set<Localities> getAll(){
                Set<Localities> localities = new HashSet<>();
                try {
                    Map<Departement, Map<Communes, List<Localities>>> map = new ConcurrentHashMap<>();
                    Region region = saveRegion("Extrême-Nord");
                    GeoJsonService geoJsonService = new GeoJsonService();
                        GeoJson geoJson = geoJsonService.readGeoJsonFile("data");
                        System.err.println(geoJson.getFeatures().size());
                        List<Feature> features = geoJson.getFeatures();
                        for (int i =0;i<10;i++){
                            Feature feature = features.get(i);
                            final List<Double> latLong = List.of(feature.getGeometry().getCoordinates().get(1), feature.getGeometry().getCoordinates().get(0));
                            HttpResponse<ReverseGeocodingResponse> response = Unirest.get("https://nominatim.openstreetmap.org/reverse?format=json&lat="+latLong.get(0)+"&lon="+latLong.get(1)+"&zoom=10&addressdetails=1")
                                    .header("accept", "application/json")
                                    .header("User-Agent", "sdasd")
                                    .asObject(new GenericType<ReverseGeocodingResponse>() {
                                    });
                            System.err.println("********************************** "+i+" ******************************************");
                            Departement departement = new Departement(null, response.getBody().getAddress().getCounty(), region);
                            Communes commune = new Communes(null, (response.getBody().getAddress().getTown() != null) ? response.getBody().getAddress().getTown() : response.getBody().getAddress().getCity(), departement);
                            Localities locality = new Localities(
                                    null,
                                    feature.getProperties().get("name") == null ? "Village "+i : String.valueOf(feature.getProperties().get("name")),
                                    latLong.get(0).toString(),
                                    latLong.get(1).toString(),
                                    LocalityType.get(String.valueOf(feature.getProperties().get("place"))), commune);
                            localities.add(locality);
                            map.computeIfAbsent(departement, d -> new ConcurrentHashMap<>())
                                    .computeIfAbsent(commune, c -> Collections.synchronizedList(new ArrayList<>()))
                                    .add(locality);
                            if(commune == null) {
                                throw new RuntimeException("ici");
                            }
                        }
                        map.forEach((departement, communesListMap) -> {
                            Departement departement1 = departement;
                            Optional<Departement> departementOp = departementsRepo.findDepartementsByName(departement.getName());
                            if(departementOp.isEmpty()) {
                                departement1 = departementsRepo.save(departement);
                            }else {
                                departement1 = departementOp.get();
                            }
                            for(Map.Entry<Communes, List<Localities>> entry : communesListMap.entrySet()){
                                Communes communes = entry.getKey();
                                communes.setDepartement(departement1);
                                communes = communeRepo.save(communes);
                                for(Localities locality : entry.getValue()){
                                    locality.setCommune(communes);
                                    localityRepo.save(locality);
                                }

                            }

                        });
                } catch (Exception exception) {
                        exception.printStackTrace();
                }
                return localities;
        }*/

        //@Override
        public void run(String... args) throws Exception {
                //Region region = saveRegion("Extrême-Nord");
            //alertRiskTypeService.init();
            //iParametres.init();
            //categories.init();




               /* try {
                        Map<Departement, Map<Communes, List<Localities>>> map = new ConcurrentHashMap<>();

                        GeoJsonService geoJsonService = new GeoJsonService();
                        GeoJson geoJson = geoJsonService.readGeoJsonFile("data");
                        System.err.println(geoJson.getFeatures().size());
                        List<Feature> features = geoJson.getFeatures();
                        IntStream.range(0, 10)//features.size())
                                .parallel() // ou .sequential()
                                .forEach(i -> {
                                        Feature feature = features.get(i);
                                        final List<Double> latLong = List.of(feature.getGeometry().getCoordinates().get(1), feature.getGeometry().getCoordinates().get(0));
                                        HttpResponse<ReverseGeocodingResponse> response = Unirest.get("https://nominatim.openstreetmap.org/reverse?format=json&lat="+latLong.get(0)+"&lon="+latLong.get(1)+"&zoom=10&addressdetails=1")
                                                .header("accept", "application/json")
                                                .header("User-Agent", "sdasd")
                                                .asObject(new GenericType<ReverseGeocodingResponse>() {
                                                });
                                        System.err.println("********************************** "+i+" ******************************************");
                                        Departement departement = new Departement(null, response.getBody().getAddress().getCounty(), region);
                                        Communes commune = new Communes(null, (response.getBody().getAddress().getTown() != null) ? response.getBody().getAddress().getTown() : response.getBody().getAddress().getCity(), departement);
                                        Localities localities = new Localities(
                                                null,
                                                feature.getProperties().get("name") == null ? "Village "+i : String.valueOf(feature.getProperties().get("name")),
                                                latLong.get(0).toString(),
                                                latLong.get(1).toString(),
                                                LocalityType.get(String.valueOf(feature.getProperties().get("place"))), commune);


                                        //String place = String.valueOf(feature.getProperties().get("place"));
                                        //String departement = response.getBody().getAddress().getCounty();
                                        //String commune = (response.getBody().getAddress().getTown() != null) ? response.getBody().getAddress().getTown() : response.getBody().getAddress().getCity();
                                        //String name = feature.getProperties().get("name") == null ? "Village "+i : String.valueOf(feature.getProperties().get("name"));
                                        //System.err.println("Place: " +place);
                                        //System.err.println("Name: " +name);
                                        //System.out.println("Departement : " + departement);
                                        //System.out.println("Commune : " +commune);
                                        //System.err.println("latLong: " + latLong);

                                        //String value = "         → "+place.trim()+": "+name;
                    */
            /*map.computeIfAbsent(departement, d -> Collections.synchronizedList(new ArrayList<>()))
                            .add(commune);*/
            /*
                                        map.computeIfAbsent(departement, d -> new ConcurrentHashMap<>())
                                                .computeIfAbsent(commune, c -> Collections.synchronizedList(new ArrayList<>()))
                                                .add(localities);

                                        if(commune == null) {
                                                throw new RuntimeException("ici");
                                        }
                                });
                        System.err.println("* Country: "+region.getPays().getName());
                        System.err.println("* Region: "+region.getName());
                        map.forEach((departement, communes) -> {
                                System.out.println("→ Département: " + departement);
                                communes.forEach((commune, villages) -> {
                                        System.out.println("    → Commune: " + commune);
                                        villages.stream()
                                                .distinct()
                                                .forEach(village -> System.out.println(village.getType()+" "+village.getName()));
                                });
                                System.out.println(); // ligne vide pour séparation
                        });
                } catch (Exception exception) {

                }*/
                try {
                        /*
                         * for (Categorie categorie : Categorie.values()) {
                         * if(repo.findByDomain(categorie).isEmpty())
                         * repo.save(new CategorieEntities(null, categorie));
                         * }
                         */
                        /*
                         * Roles roles1 = serviceRole.save(new Roles(null,
                         * RolesName.SUPERADMIN.toString(),"Super utilisateur","all"));
                         * Roles roles2 = serviceRole.save(new Roles(null,
                         * RolesName.ADMIN.toString(),"Admin","all"));
                         * Roles roles3 = serviceRole.save(new Roles(null,
                         * RolesName.ROOT.toString(),"Utilisateur Root","all"));
                         * Roles roles4 = serviceRole.save(new Roles(null,
                         * RolesName.USER.toString(),"Utilisateur","all"));
                         * Roles roles5 = serviceRole.save(new Roles(null,
                         * RolesName.VISITOR.toString(),"Visiteur","all"));
                         * Roles roles6 = serviceRole.save(new Roles(null,
                         * RolesName.GESTIONNAIRECENTRE.toString(),"Gestionnaire de centre","all"));
                         * Groupes groupes2 = new Groupes(
                         * null,
                         * new HashSet<>(),
                         * RolesName.USER.toString(),
                         * "Groupe des Utilisateurs",
                         * "Description",
                         * new HashSet<>());
                         * groupes2.getRoles().add(serviceRole.findByNom(RolesName.USER.toString()).get(
                         * ));
                         * serviceGroupe.save(groupes2);
                         */
                        UtilisateurDTO dto = new UtilisateurDTO();
                        dto.setEmail("youthfp@youthfp.cm");
                        dto.setTelephone("@Youthfp75");
                        dto.setNom("Youthfp");
                        dto.setPrenom("Inc");
                        dto.setStatus(Status.ACTIVE);

                        /*
                         * if(serviceAdmin.codeExist("000000000000000")){
                         * Administrateur administrateur = new Administrateur();
                         * administrateur.getRoles().add(roles1);
                         * administrateur.setCode("000000000000000");
                         * administrateur.setNom("Administrateur");
                         * administrateur.setPrenom("Administrateur");
                         * administrateur.setEmail("root@test.com");
                         * administrateur.setPwd("1234");
                         * administrateur.setdCreation(new Date());
                         * administrateur.getRoles().add(roles1);
                         * serviceAdmin.save(administrateur);
                         * }
                         */
                        /*
                         * Groupes groupes = new Groupes(
                         * null,
                         * new HashSet<>(),
                         * RolesName.SUPERADMIN.toString(),
                         * "Groupe des SUPERADMIN",
                         * "Description",
                         * new HashSet<>());
                         * groupes.getRoles().add(roles1);
                         * groupes.getRoles().add(roles2);
                         * groupes.getRoles().add(roles3);
                         * groupes.getRoles().add(roles4);
                         * groupes.getRoles().add(roles5);
                         * groupes.getRoles().add(roles6);
                         * groupes = serviceGroupe.save(groupes);
                         */

                        // iUtilisateur.save(dto,153L);
                        /*
                         * Groupes groupes1 = new Groupes(
                         * null,
                         * new HashSet<>(),
                         * RolesName.ADMIN.toString(),
                         * "Groupe des administrateurs",
                         * "Description",
                         * new HashSet<>());
                         * groupes1.getRoles().add(roles2);
                         * serviceGroupe.save(groupes1);
                         */
                    List<Groupes> groupes = groupesRepository.findAll().stream().filter(groupe->groupe.getPermissions() == null || groupe.getPermissions().isEmpty()).toList();
                    final Set<PermissionEnum> permissionEnums = Arrays.stream(PermissionEnum.values()).collect(Collectors.toSet());
                    for (Groupes groupe : groupes) {
                        RolesName role;
                        try {
                            role = RolesName.valueOf(groupe.getName());
                        } catch (IllegalArgumentException e) {
                            continue; // ignore si le nom ne correspond pas à un enum
                        }
                        Set<PermissionEnum> permissions = permissionEnums.stream().filter(permission -> Arrays.stream(permission.getRoles()) .anyMatch(r -> r == role) ) .collect(Collectors.toSet());
                        initializePermissionsIfNeeded(groupe, permissions);
                    }
                } catch (Exception e) {
                }

        }
        @Transactional
        public void initializePermissionsIfNeeded(Groupes groupe, Set<PermissionEnum> permissions){
            groupe.setPermissions(permissions);
            groupesRepository.save(groupe);

        }
}
