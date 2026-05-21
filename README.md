# Adventurer Training Lesson - 2D Java Action Platformer

Welcome to **Adventurer Training Lesson**, a 2D side-scrolling action-platformer game developed completely in **Java SE** using the **Swing** and **AWT (Graphics2D)** frameworks. This project serves as a comprehensive, real-time implementation environment for advanced Object-Oriented Programming (OOP) concepts, architectural Design Patterns, and clean software development principles (SOLID).

---

## 🎮 Game Core Mechanics
* **Dynamic Physics Engine:** Axis-Aligned Bounding Box (AABB) custom collision mapping for platform alignment and gravity calculations.
* **Environmental Hazards:** Treacherous vertical thresholds—plunging past the abyss boundary ($Y > 600$) triggers an instantaneous transition into the Game Over sequence.
* **Advanced Boss & Enemy AI:** Features a multi-tiered final challenge (`Boss_Golem`) utilizing variable delay attacks (Laser beam subimage slicing, Stone Spike spatial summoning) and an Enraged dynamic glow form phase.

---

## 🛠️ Software Engineering & Architecture

### 1. Core OOP Pillars
* **Encapsulation:** Critical entity telemetry (Health pools, local vector coordinates, movement thresholds) are securely bounded inside `private`/`protected` scopes, modifiable exclusively via regulated mutator pipelines (e.g., `damageEnemy()`).
* **Abstraction:** The main engine loop operates seamlessly via high-level polymorphic structural triggers (`player.update()`, `boss.update()`), completely isolated from the granular micro-logic underneath.
* **Inheritance:** An optimized classification taxonomy driven by a centralized parent `Entity` class to enforce structural code reuse (DRY principle) across `Player`, `Boss_Golem`, and basic mob units.
* **Polymorphism:** Runtime dynamic dispatch execution through method overriding within rendering passes and functional asset streaming grids (`for (Entity enemy : gp.enemies)`).

### 2. Implemented Design Patterns
* **Game Loop Pattern with Delta Time:** Anchors update and rendering executions to a strict 60 FPS constraint, stabilizing gameplay mechanics globally across variable hardware CPU configurations.
* **Lightweight State Pattern:** Eliminates unstable conditional branching loops by tracking and pivoting character actions smoothly using localized operational states (`idle`, `isAiming`, `isAttacking`, `isEnraged`).
* **Mediator Hub Structure:** Leverages `GamePanel` as the sole centralized synchronization network routing collision intersections and cross-entity state mutations cleanly.

### 3. SOLID Principles Compliance
* **S**ingle Responsibility (SRP) — Specialized sub-modules like `Boss_Laser` focus strictly on specialized boundary math.
* **O**pen/Closed (OCP) — Core engine loops remain closed to modifications while entity models remain fully open to scaling expansions.
* **L**iskov Substitution (LSP) — Abstract collections fluidly substitute specific child extensions interchangeably.
* **I**nterface Segregation (ISP) — Lightweight input processing mapping isolated `KeyListener` callbacks.
* **D**ependency Inversion (DIP) — State machine mechanics bind to abstract framework constraints rather than concrete vector parameters.

---

## 👥 Development Team & Contributions
* **Member 1 (OOP Fundamentals Architect):** Core structural taxonomy design, Abstraction & Encapsulation data safeguards, and UML Class Diagram orchestration.
* **Member 2 (Polymorphism & Inheritance Master):** Entity lineage integration, dynamic multi-tier override rendering pipelines, and generic dynamic mob list managers.
* **Member 3 (Design Patterns & Loop Engineer):** Real-time Delta Time Game Loop synchronization, Lightweight State machine flags implementation, and Central Hub communication network.
* **Member 4 (Physics & Platform Collision Specialist):** Custom Axis-Aligned Bounding Box (AABB) intersection engine, horizontal/vertical platform snapping rules, and out-of-bounds abyss threshold ($Y > 600$) game termination tracking.
* **Member 5 (SOLID Principles Reviewer - S/O/L):** Code factorization for Single Responsibility compliance, Open/Closed abstract scalability, and Liskov substitution correctness verification.
* **Member 6 (SOLID Principles Reviewer - I/D & UI Systems):** Lightweight user input listener decoupling, structural Dependency Inversion framework compliance, and Win/Lose state real-time overlay graphics renderers.

---

## 🚀 How to Run the Project
1. Clone the repository locally: `git clone https://github.com/your-username/Adventurer-Training-Lesson.git`
2. Open your preferred IDE (VS Code, Eclipse, or IntelliJ IDEA).
3. Ensure you have **Java JDK** (v17 or higher recommended) configured in your system environment path.
4. Import the root project folder, navigate to the main runtime driver class, and execute the application.
