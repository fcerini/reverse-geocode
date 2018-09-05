# reverse-geocode
Small Java command line utility to reverse geocode a `input.csv` file using openstreetmap (nominatim).

Visual Studio Code, springframework.boot, Gradle

```
Example input.csv file: Lon, Lat (X,Y, other fields)
-58.7062114999994,-34.4229834999998, 1
-58.382857,-34.595936, 100
-58.4962295, -34.546371, 101
-58.54344, -34.5352185, 102
-58.6440954999993, -34.4856159999997, 999

```

```
Example outputYYYY_MM_DD_hh_mm_osm.csv file
-58.7062114999994,-34.4229834999998, 1,0,Barrio Valle Claro,highway,living_street,road
-58.382857,-34.595936, 100,1072,Cerrito,place,house,place
-58.4962295, -34.546371, 101,3147,Mejico,place,house,place
-58.54344, -34.5352185, 102,3202,Cerrito,place,house,place
-58.6440954999993, -34.4856159999997, 999,0,Basualdo,highway,residential,road

```

```
Example outputYYYY_MM_DD_hh_mm_here.csv file
-58.7062114999994,-34.4229834999998, 1,0,Valle Claro,street,address,SN
-58.382857,-34.595936, 100,1070,Cerrito,houseNumber,address,pointAddress
-58.4962295, -34.546371, 101,3151,Méjico,houseNumber,address,interpolated
-58.54344, -34.5352185, 102,6672,5 de Julio,houseNumber,address,pointAddress
-58.6440954999993, -34.4856159999997, 999,1326,Basualdo,houseNumber,address,interpolated

```
