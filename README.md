# Empath

**Empath** is a cross-platform application for job search, resume building, article publishing, and interview preparation — built entirely with Kotlin Multiplatform (KMP). The project is in active development and serves as both a real-world product prototype and an architectural playground.

---

## Project Goals

- Explore Kotlin Multiplatform (KMP) in a full-scale application targeting **Android**, **iOS**, and **Desktop**.
- Compare modern architectural solutions (e.g., Decompose, Gradle Convention Plugins) with traditional Android stack used in commercial projects.
- Build a reusable, scalable project template based on **Clean Architecture** and **feature-based modularization**.
- Improve architectural thinking and developer visibility beyond platform boundaries.

---

## Tech Stack

### Core:
- **Kotlin Multiplatform** – Shared codebase across Android, iOS, Desktop.
- **Clean Architecture** – Clear separation of concerns across layers.
- **Modularization** – Feature-based modular structure for scalability and maintainability.

### Infrastructure:
- **Gradle Convention Plugins** – Standardized build logic and dependency management.
- **Koin** – Dependency injection framework.
- **Ktor + Ktorfit** – Typed REST client built on top of Ktor for network communication.
- **DataStore** – Multiplatform key-value storage.
- **Coil** – Image loading.

### Architecture & Navigation:
- **Decompose** – Navigation + MVI for all KMP targets.

### Design:
- **Figma UI Kit** – Design system created in Figma  
  [Figma link](https://www.figma.com/design/2wDsNvMCxN8Yaku307S3t2/empath-ui-kit?node-id=86-5475&t=c2hltXsLG7NsMO65-1)

### Code Confirmation

| Type    | Theme | Screenshot                                                             |
|---------|-------|------------------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/code-confirmation-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/code-confirmation-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/code-confirmation-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/code-confirmation-tablet-dark.png" width="750">  |

### Email Verification

| Type    | Theme | Screenshot                                                              |
|---------|-------|-------------------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/email-verification-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/email-verification-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/email-verification-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/email-verification-tablet-dark.png" width="750">  |

### Loading

| Type    | Theme | Screenshot                                                   |
|---------|-------|--------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/loading-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/loading-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/loading-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/loading-tablet-dark.png" width="750">  |

### Login

| Type    | Theme | Screenshot                                                 |
|---------|-------|------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/login-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/login-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/login-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/login-tablet-dark.png" width="750">  |

### Password Recovery

| Type    | Theme | Screenshot                                                             |
|---------|-------|------------------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/password-recovery-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/password-recovery-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/password-recovery-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/password-recovery-tablet-dark.png" width="750">  |

### Post Create

| Type    | Theme | Screenshot                                                       |
|---------|-------|------------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/post-create-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/post-create-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/post-create-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/post-create-tablet-dark.png" width="750">  |

### Post Detail

| Type    | Theme | Screenshot                                                       |
|---------|-------|------------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/post-detail-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/post-detail-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/post-detail-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/post-detail-tablet-dark.png" width="750">  |

### Profile Edit

| Type    | Theme | Screenshot                                                        |
|---------|-------|-------------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/profile-edit-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/profile-edit-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/profile-edit-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/profile-edit-tablet-dark.png" width="750">  |

### Profile

| Type    | Theme | Screenshot                                                   |
|---------|-------|--------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/profile-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/profile-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/profile-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/profile-tablet-dark.png" width="750">  |

### Registration

| Type    | Theme | Screenshot                                                        |
|---------|-------|-------------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/registration-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/registration-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/registration-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/registration-tablet-dark.png" width="750">  |

### Vacancies

| Type    | Theme | Screenshot                                                     |
|---------|-------|----------------------------------------------------------------|
| Mobile  | White | <img src="screenshots/vacancies-screen-white.png" width="250"> |
| Mobile  | Dark  | <img src="screenshots/vacancies-screen-dark.png" width="250">  |
| Desktop | White | <img src="screenshots/vacancies-tablet-white.png" width="750"> |
| Desktop | Dark  | <img src="screenshots/vacancies-tablet-dark.png" width="750">  |

### Vacancy Filters

| Type   | Theme | Screenshot                                                           |
|--------|-------|----------------------------------------------------------------------|
| Mobile | White | <img src="screenshots/vacancy-filters-screen-white.png" width="250"> |
| Mobile | Dark  | <img src="screenshots/vacancy-filters-screen-dark.png" width="250">  |


---

## Modules (Planned)

- `:empath-app`
- `:core:uikit`
- `:core:network`
- `:core:utils`
- `:features:auth`
- `:features:profile`
- `:features:posts`
- `:features:file-storage`
- `:features:vacancies`

---

## Status

🔧 **In development** – expect rapid iterations, experimental features, and architectural exploration.

---

## Author
**Dias Kaiyrzhan**  
Android Developer | Kotlin Multiplatform Enthusiast  

