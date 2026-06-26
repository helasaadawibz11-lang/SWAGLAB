Feature: Tester la deconnexion du site "Swaglab" Module Logout


  Background:  Login avec les identifiants valides
    Given je suis sur la page Login
    When je saisi l'username "standard_user"
    And je saisi le mot de passe "secret_sauce"
    And je click sur le bouton Login
    Then redirection vers la page Home

#SMOKE
  Scenario: Tester le logout du site
    Given je suis sur la page Home
    When je click sur le bouton Menu
    And je click sur le bouton logout
    Then je suis redirigé vers la page Login

#REGRESSION
  Scenario: Tester le bouton retour a la page home apres logout
    Given je suis sur la page Home
    When je click sur le bouton Menu
    And je click sur le bouton logout
    And je click sur le bouton retour de la pageweb
    Then affichage Erreurmsg "Epic sadface: You can only access '/inventory.html' when you are logged in."


  #security #regression

  Scenario: Vérifier la destruction de la session après fermeture de l'onglet
    Given je suis sur la page Home
    When je click sur le bouton Menu
    And je click sur le bouton logout
    And je ferme l'onglet du navigateur
    And je réouvre le site SwagLabs
    Then je suis toujours sur la page Login et non connecté

  #security

  Scenario Outline: Vérifier le blocage d'accès aux URLs internes après déconnexion
    Given je suis sur la page Home
    When je click sur le bouton Menu
    And je click sur le bouton logout
    And je tente daccéder directement à l URL "<url_interne>"
    Then je suis redirigé vers la page Login
    And un msg derreur de session doit s'afficher

    Examples:
      | url_interne                                      |
      | https://www.saucedemo.com/cart.html              |
      | https://www.saucedemo.com/checkout-step-one.html |