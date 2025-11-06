# K-vov — Variable Object Validation DSL

K-vov is a lightweight, declarative validation DSL for Kotlin.
It allows you to build expressive, type-safe validation logic for strings, numbers, and collections with clean, readable syntax.

---

## 🧬 Installation

Add dependency (via **JitPack**):

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.sean-nam-dev:kvov:$version")
}
```

---

## ⚙️ Basic Usage

### Validate strings

```kotlin
val password = validate("MyP@ssw0rd") {
    length(min = 8, max = 20)
    whitespace(allow = false)
}

when (password) {
    is Result.Success -> println("✅ Password valid")
    is Result.Failure -> println("❌ Errors: ${password.errors}")
}
```

---

### Validate numbers

```kotlin
val value = validate(42.0) {
    range(0.0..100.0)
    positive()
    even()
}

println(value) // Result.Success
```

---

### Validate collections

```kotlin
val items = validate(listOf(10, 20, 30, 40)) {
    size(min = 2, max = 10)
    distinct()
    contains(10, 20)
    all { it > 0 }
}

println(items) // Result.Success
```

---

## ✅ Result Model

Every validation returns one of two outcomes:

```kotlin
sealed class Result {
    data object Success : Result()
    data class Failure(val errors: List<Error>) : Result()
}
```

Errors are defined in typed groups:

```kotlin
Error.String.MIN_LENGTH
Error.Number.POSITIVE
Error.Collection.DISTINCT
```

---

## 💡 Example: Combined Validation

```kotlin
val data = validate("Abc#12345") {
    length(min = 6, max = 12)
    digit(min = 2)
    symbol(min = 1)
    uppercase(min = 1)
    lowercase(min = 1)
}
```

---

## 🧠 Design Philosophy

* Declarative, type-safe DSL syntax
* Minimal runtime overhead
* Composable validation blocks
* Explicit error typing per data category
* Fully covered by JUnit4 tests

---

## 🗾 License

K-vov is distributed under the MIT License.
© 2025 Shakhrukhkhon Namazov
