# 🧪 SwagLabs E2E Automation Framework

Ce projet implémente un framework d'automatisation de tests de bout en bout (E2E) pour le site e-commerce **SwagLabs** (Saucedemo).  
Construit avec une approche **BDD (Behavior-Driven Development)**, il valide la robustesse, la sécurité et la non-régression du parcours utilisateur.

---

## 📊 Tableau de Bord des Tests (QA Dashboard)

| Métrique | Indicateur | Description |
| :--- | :---: | :--- |
| **Total Scénarios de Test** | **23** | Scénarios automatisés globaux |
| **Couverture Fonctionnelle** | **100%** | Modules critiques du tunnel d'achat couverts |
| **Types de Tests** | `Smoke` `Regression` `Security` `UI/UX` | Approche de test multi-niveaux |
| **Design Pattern** | `POM` + `Page Factory` | Architecture modulaire et découplée |

---

### 📈 Répartition des Cas de Test par Module


📦 Total Suite (23 Scénarios)
├── 🔐 Login           [████████] 5 Scénarios (incluant 11 jeux de données)
├── 🚪 Logout          [██████]   4 Scénarios
├── 🛍️ Product Page    [█████████] 6 Scénarios
└── 💳 Checkout        [████████]   8 Scénarios

---
#### 🗺️ **Matrice de Couverture Fonctionnelle**

| Module | Type de Test | Objectif du Scénario | Statut |
| :--- | :---: | :--- | :---: |
| **🔐 Login** | `Nominal` | Connexion utilisateur standard avec identifiants valides | ✅ |
| | `Sécurité` | Robustesse face aux injections SQL (`OR 1=1 --`) & caractères spéciaux | ✅ |
| | `Sécurité` | Blocage d'accès direct aux pages internes sans session active | ✅ |
| | `UI / UX` | Soumission du formulaire via la touche `Entrée` du clavier | ✅ |
| | `UI / UX` | Masquage des caractères dans le champ Mot de Passe | ✅ |
| **🚪 Logout** | `Nominal` | Déconnexion complète via le menu et redirection | ✅ |
| | `Régression` | Interdiction de ré-accès via le bouton "Précédent" du navigateur | ✅ |
| | `Sécurité` | Destruction de session après fermeture/réouverture de l'onglet | ✅ |
| | `Sécurité` | Blocage d'accès direct aux URLs profondes (`/cart.html`, etc.) | ✅ |
| **🛍️ Products** | `Fonctionnel` | Ajout au panier depuis la liste principale ET la page détail | ✅ |
| | `Régression` | Persistance du panier après rafraîchissement de la page (`F5`) | ✅ |
| | `UI / UX` | Incrémentation et décrémentation dynamique du badge compteur | ✅ |
| | `Fonctionnel` | Tri et filtrage alphabétique des produits (Z à A) | ✅ |
| | `UI / UX` | Conformité visuelle de l'icône panier par rapport à une référence | ✅ |
| | `Fonctionnel` | Cohérence des données (Nom, Prix, Description) sur la page détail | ✅ |
| **💳 Checkout** | `Nominal` | Tunnel d'achat complet avec informations valides (*Happy Path*) | ✅ |
| | `Données` | Validation des champs obligatoires via `Scenario Outline` (6 cas limites) | ✅ |
| | `Calculs` | Vérification mathématique exacte (Prix + Taxe = Total) | ✅ |
| | `Régression` | Annulation à l'étape 1 ou 2 avec contrôle de persistance du panier | ✅ |
| | `Limites` | Tentative de Checkout avec un panier vide (Détection d'anomalie) | ✅ |
| | `Navigation` | Persistance du panier en cas de retour arrière navigateur | ✅ |

---

🏛️ **Architecture du Framework**
Le framework applique rigoureusement le modèle POM (Page Object Model) couplé à Page Factory
afin d'assurer l'indépendance entre la description graphique des pages et la logique de test.
