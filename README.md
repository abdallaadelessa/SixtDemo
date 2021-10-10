# Sixt Demo

Demo app for illustrating how to write testable code using the MVVM architecture and the Jetpack libraries

The app is divided into 3 packages

- Data -> Data Layer (get the data from data source)
- Domain -> Business layer (do some processing on the data)
- UI -> Presentation layer (present the data)

# Screenshots

## Dark Theme
<img src="/screenshots/dark/1.png" width="200"> <img src="/screenshots/dark/2.png" width="200"> <img src="/screenshots/dark/3.png" width="200">

## Light Theme
<img src="/screenshots/light/1.png" width="200"> <img src="/screenshots/light/2.png" width="200"> <img src="/screenshots/light/3.png" width="200">

# Libraries

### Multithreading

- Kotlin Coroutines

### Network

- OkHttp
- Retrofit

### Image Loader

- Coil

### Dependency Injection

- Hilt

### Json Parsing

- Kotlin Serialization

### UI

- Jetpack Compose

# Unit Tests
- Using dependency injection helps to separate the units to be tested from it's dependencies<br>
[CarsServiceImplTest](app/src/test/java/com/abdullahessa/sixtdemo/domain/cars/service/CarsServiceImplTest.kt)

- Using a view model helps to test the UI logic in local unit tests<br>
[HomeViewModelTest](app/src/test/java/com/abdullahessa/sixtdemo/ui/screen/home/model/HomeViewModelTest.kt)

# UI Tests
- [ListScreenTest](app/src/androidTest/java/com/abdullahessa/sixtdemo/ui/screen/home/tabs/ListScreenTest.kt)
- [MapScreenTest](app/src/androidTest/java/com/abdullahessa/sixtdemo/ui/screen/home/tabs/MapScreenTest.kt)

# Apk
- [SixtDemo.apk](apk/sixtDemo.apk)
