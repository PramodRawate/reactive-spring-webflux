# Reactive Programming - Spring Webflux
This repo contains reactive programming examples implemented using **Spring Webflux** which uses **project reactor** as a library.

Most of the examples are implemented using TDD approach. Hence, you'll find the test cases(in test package) implemented for spring webflux concepts.

Please find below the spring webflux concepts that are implemented in the given repo:-
**Mono**:- a Mono object represents a single-value-or-empty (0..1).
**Flux**:- A Flux object represents a reactive sequence of 0..N.
We can use **Flux.concateWith()** method to add elements to an existing flux.

### Factory methods on Flux and Mono
**1. Flux.fromIterable(Iterable<? extends T) -** Creates a FLux from passed Iterable.
```shell
List<String> names = Arrays.asList("Adam", "Jack", "John", "Jenny");
Flux<String> namesFlux = Flux.fromIterable(names);
```

**2. Flux.fromArray(T[] array> it) -** Creates a flux from passed array.
```shell
String[] names = new String[] {"Adam", "Jack", "John", "Jenny"};
Flux<String> namesFlux = Flux.fromArray(names);
```

**3. Flux.fromStream(Stream<? extends T> s) -** creates a Flux from given Stream.
```shell
List<String> names = Arrays.asList("Adam", "Jack", "John", "Jenny");
Flux<String> namesFlux = Flux.fromStream(names.stream())
```

**4. Flux.range(int start, int count) -** creates a flux of Integers with given range.
```shell
Flux<Integer> integerFlux = Flux.range(1, 5);
```

**5. Mono.justOrEmpty(@Nullable Optional<? extends T> data) -** Creates a flux of empty of passed data which is
optional. Used in case if we want to create an empty mono.
```shell
Mono<Object> mono = Mono.justOrEmpty(null);
```

**5. Mono.fromSupplier(Supplier<? extends T> supplier) -** Used to create a Mono from passed supplier which will produce the value for the mono.
```shell
Supplier<String> stringSupplier = () -> "Marcus";
Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
```


## Flux and Mono Operations
**1. merge(flux1,flux2) -** Merges 2 flux but order cannot be guaranteed in the merged Flux. When used with delays 2nd flux doesn't wait for 1st flux. Both run in parallel.

**2. concat(flux1, flux2) -** Concat 2 flux but order is guaranteed. When used with delay 2nd flux waits for 1st flux. Hence, for 3 elements in 1st flux with delay 2nd flux waits 3 seconds(1 sec for 1 element).

**3. zip(flux1, flux2 (t1, t2)) -**  Zip() is used when we want to combine elements from 1st and 2nd flux. t1, t2 are 1st element from 1st and 2nd flux respectively.

## Error handling in Webflux
**1. onErrorResume(() -> {}) -**  Do something such as performing some operation, returning a flux with default values when Exception occurs.,etc.

**2. onErrorReturn() -**  Return a default value when exception occurs

**3. onErrorMap() -** map the occurred exception to some other exception.

### Filter Operation
Used to filter out specific elements form a flux by providing a filter condition as follows:-

```shell
List<String> names = Arrays.asList("Adam", "Jack", "Anna", "John", "Jenny", "Antony");
Flux<String> stringFlux = Flux.fromIterable(names)
                .filter(name -> name.startsWith("A"))
```
The resultant flux will contain 3 elements as :
```shell
{"Adam", "Anna", "Antony"}
```

### Transform Operation on flux/Mono
Used to create a flux by applying a map function on input flux and create a new flux.
Example below shows how to convert flux elements to uppercase using map() function;
```shell
List<String> names = Arrays.asList("Adam", "Jack", "Jay", "Jenny");
Flux<String> namesFlux = Flux.fromIterable(names)
                .map(name -> name.toUpperCase(Locale.ROOT))
```
The output flux will have uppercase elements as follows:-

```shell
"ADAM", "JACK", "JAY", "JENNY"
```

### Back Pressure Support
Subscriber can control the data flow from Publisher using following method:
```shell
thenRequest(long n):
``` 
Request the given amount of elements from the upstream Publisher.

Also we can use **BaseSubscriber** - A simple base class for a Subscriber implementation that lets the user perform a **request(long)** and **cancel()** on it directly based on the specified conditions.

Ex: Refer **FluxAndMonoBackPressureTest.java** for the examples. 
