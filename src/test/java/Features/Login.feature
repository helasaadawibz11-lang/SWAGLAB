Feature: Tester la connexion au site "Swaglab" Module Login


  Scenario: Test Login avec les identifiants valides
    Given je suis sur la page Login
    When je saisi l'username "standard_user"
    And je saisi le mot de passe "secret_sauce"
    And je click sur le bouton Login
    Then redirection vers la page Home


  Scenario Outline: Test login avec identifiants invalides
    Given je suis sur la page Login
    When je saisi l'username "<username>"
    And je saisi le mot de passe "<password>"
    And je click sur le bouton Login
    Then un msg derreur doit safficher "<msgErreur>"
    Examples:
      | username        | password     | msgErreur                                                   |
      | standard_user   |              | Epic sadface: Password is required                          |
      | standard_user   | secret       | Username and password do not match any user in this service |
      | standard_use    | secret_sauce | Username and password do not match any user in this service |
      |                 | secret_sauce | Epic sadface: Username is required                          |
      | standard_use    | secret_sauc  | Username and password do not match any user in this service |
      | STANDARD_USER   | secret_sauce | Username and password do not match any user in this service |
      | standard_user   | SECRET_SAUCE | Username and password do not match any user in this service |
      | OR 1=1 --       | secret_sauce | Username and password do not match any user in this service |
      | é&###&é&        | secret_sauce | Username and password do not match any user in this service |
      | standard_user   | é&###&é&     | Username and password do not match any user in this service |
      | locked_out_user | secret_sauce | Sorry, this user has been locked out.                       |

         #username existant password vide
         #username existant mauvais password
         #username erroné password correct
         #username vide password correct
         #username et password erroné
         #username existant mais mauvaise casse (ex: si inscrit avec E majuscule)
         #Mot de passe existant mais mauvaise casse ( majuscule)
         #Sécurité : Tentative d'injection SQL
        # tentative d'injection caracteres speciaux (password or username)


  #security

  Scenario: Vérifier le blocage de l'accès direct aux pages internes sans session
    Given je tente d'accéder directement à la page "https://www.saucedemo.com/inventory.html"
    Then je suis redirigé vers la page Login

    #UI

  Scenario: Vérifier la soumission du formulaire via la touche Entrée
    Given je suis sur la page Login
    When je saisi l'username "standard_user"
    And je saisi le mot de passe "secret_sauce"
    And j'appuie sur la touche Entrée du clavier
    Then redirection vers la page Home


  #security #ui

  Scenario: Vérifier le masquage du mot de passe dans le formulaire
    Given je suis sur la page Login
    When je saisi le mot de passe "secret_sauce"
    Then les caractères saisis dans le champ mot de passe doivent être masqués