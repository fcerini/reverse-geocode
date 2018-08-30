# reverse-geocode
Small Java command line utility to reverse geocode a `input.csv` file using openstreetmap (nominatim).

Visual Studio Code, springframework.boot, Gradle

```
Example input.csv file: Lon, Lat (X,Y, other fields)
-58.4962295, -34.546371, bla, bla
-58.54344, -34.5352185, bla, bla
```

```
Example outputYYYY_MM_DD_hh_mm.csv file
-58.4962295, -34.546371, bla, bla, 3147, Mejico, Villa Martelli, Vicente López, Partido de Vicente López, Bs. As., 1603, Argentina
-58.54344, -34.5352185, bla, bla, 3202, Cerrito, Villa Adelina, Vicente López, Partido de Vicente López, Bs. As., B1652DWY, Argentina
```
