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

```text
📦 Total Suite (23 Scénarios)
├── 🔐 Login           [████████] 5 Scénarios (incluant 11 jeux de données)
├── 🚪 Logout          [██████]   4 Scénarios
├── 🛍️ Product Page    [█████████] 6 Scénarios
└── 💳 Checkout        [████████]   8 Scénarios


🗺️ Matrice de Couverture FonctionnelleModuleType de TestObjectif du ScénarioStatut🔐 LoginNominalConnexion utilisateur standard avec identifiants valides✅SécuritéRobustesse face aux injections SQL (OR 1=1 --) & caractères spéciaux✅SécuritéBlocage d'accès direct aux pages internes sans session active✅UI / UXSoumission du formulaire via la touche Entrée du clavier✅UI / UXMasquage des caractères dans le champ Mot de Passe✅🚪 LogoutNominalDéconnexion complète via le menu et redirection✅RégressionInterdiction de ré-accès via le bouton "Précédent" du navigateur✅SécuritéDestruction de session après fermeture/réouverture de l'onglet✅SécuritéBlocage d'accès direct aux URLs profondes (/cart.html, etc.)✅🛍️ ProductsFonctionnelAjout au panier depuis la liste principale ET la page détail✅RégressionPersistance du panier après rafraîchissement de la page (F5)✅UI / UXIncrémentation et décrémentation dynamique du badge compteur✅FonctionnelTri et filtrage alphabétique des produits (Z à A)✅UI / UXConformité visuelle de l'icône panier par rapport à une référence✅FonctionnelCohérence des données (Nom, Prix, Description) sur la page détail✅💳 CheckoutNominalTunnel d'achat complet avec informations valides (Happy Path)✅DonnéesValidation des champs obligatoires via Scenario Outline (6 cas limites)✅CalculsVérification mathématique exacte (Prix + Taxe = Total)✅RégressionAnnulation à l'étape 1 ou 2 avec contrôle de persistance du panier✅LimitesTentative de Checkout avec un panier vide (Détection d'anomalie)✅NavigationPersistance du panier en cas de retour arrière navigateur✅

🏛️ Architecture du FrameworkLe framework applique rigoureusement le modèle POM (Page Object Model) couplé à Page Factory afin d'assurer l'indépendance entre la description graphique des pages et la logique de test.
