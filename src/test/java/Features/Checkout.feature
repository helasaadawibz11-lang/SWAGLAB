Feature: tester la gestion du checkout , verification de la commande et validation du tunnel d'achat Complet


  Background: Connexion à l'application
    Given je suis sur la page Login
    When je saisi l'username "standard_user"
    And je saisi le mot de passe "secret_sauce"
    And je click sur le bouton Login
    Then redirection vers la page Home



    #Happypath checkout
  Scenario: Finaliser une commande avec des informations valides
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je click checkout
    And je saisi Firstname "Hela"
    And je saisi Lastname "S"
    And je saisi Codepostal "34000"
    And je click Continue
    And je click Finish
    Then successful checkout

  #données invalides
  Scenario Outline: test de la fonction Checkout avec des données invalides
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je click checkout
    And je saisi Firstname "<firstname>"
    And je saisi Lastname "<lastname>"
    And je saisi Codepostal "<codepostal>"
    And je click Continue
    Then Checkout failed affichage msg "<Expectedmsg>"
    Examples:
      | firstname | lastname | codepostal    | Expectedmsg                    |
      | hela      |          | 5060          | Error: Last Name is required   |
      |           | sd       | 5060          | Error: First Name is required  |
      |           |          |               | Error: First Name is required  |
      | hela      | sd       |               | Error: Postal Code is required |
      | &é#&é#    | &é#&é#   | &é#&é#        | Error:                         |
      | hela      | sd       | codeenlettres | Error: Postal Code is required |



    #Tunel d'achat et verification des prix et calcul
  Scenario: verifier total checkout dans l'interface de validation checkout
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je click checkout
    And je saisi Firstname "hela"
    And je saisi Lastname "SD"
    And je saisi Codepostal "5060"
    And je click Continue
    Then le total checkout est correct



  #verification disponibilité données de paiement checkout dans la page checkout
  Scenario: verifier les données de paiement dans l'interface checkout
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je click checkout
    And je saisi Firstname "hela"
    And je saisi Lastname "SD"
    And je saisi Codepostal "5060"
    And je click Continue
    Then les données Payment Information ,Shipping Information et Price Total sont disponibles


  #ANNULATION DU PROCESSUS DE CHECKOUT (scenario  1 , scenario 2 , scenario 3 )

    #Annulation checkout depuis la page de saisie des infos scenario  1 : persistance panier
  Scenario: Annuler le checkout sur la page de saisie des informations
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je recupere les noms des articles de mon panier
    And je click checkout
    And je click sur le bouton Cancel
    Then je suis redirigé vers la page du panier
    And mon panier affiche toujours les articles ajoutés


    #Annulation checkout depuis la page récapitulatif de la commande scenario  2 : redirection page home
  Scenario: Annuler le checkout sur la page de récapitulatif de la commande ( redirection page home)
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je recupere les noms des articles de mon panier
    And je click checkout
    And je saisi Firstname "Hela"
    And je saisi Lastname "S"
    And je saisi Codepostal "34000"
    And je click Continue
    And je click sur le bouton Cancel
    Then redirection vers la page Home


    #Annulation checkout depuis la page récapitulatif de la commande scenario  2 : persistance panier
  Scenario: Annuler le checkout sur la page de récapitulatif de la commande ( persistance panier )
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je recupere les noms des articles de mon panier
    And je click checkout
    And je saisi Firstname "Hela"
    And je saisi Lastname "S"
    And je saisi Codepostal "34000"
    And je click Continue
    And je click sur le bouton Cancel
    And je j'accede au panier
    Then mon panier affiche toujours les articles ajoutés


  #Tester l'etat PANIER et LE BOUTON BACK TO PRODUCTS apres  validation achat
  Scenario: Retourner à la boutique après un achat validé
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je click checkout
    And je saisi Firstname "Hela"
    And je saisi Lastname "S"
    And je saisi Codepostal "34000"
    And je click Continue
    And je click Finish
    And je click sur le bouton Back Home
    Then redirection vers la page Home
    And Mon panier est vide apres validation achat



  #Passer au CHECKOUT AVEC PANIER VIDE
  Scenario: Tenter de faire un checkout sans aucun article dans le panier
    Given je suis sur la page d'accueil avec un panier vide
    When je j'accede au panier
    And je click checkout
    And je saisi Firstname "Hela"
    And je saisi Lastname "S"
    And je saisi Codepostal "34000"
    And je click Continue
    And je click Finish
    Then le système bloque le checkout avec la non redirection vers la page Checkout Complete



  # PERSISTANCE EN CAS DE RETOUR EN ARRIÈRE (NAVIGATEUR)
  Scenario: Vérifier la persistance des produits du panier après un retour arrière du navigateur
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je recupere les noms des articles de mon panier
    And je click checkout
    And je fais un retour arrière avec le navigateur
    Then je suis redirigé vers la page du panier
    And mon panier affiche toujours les articles ajoutés



    @test
    #Tester la Destruction de la session et blocage après retour forcé à la page Login
  Scenario: Destruction de la session et blocage après retour forcé à la page Login
    Given j'ai des articles dans mon panier et je suis sur mon panier
    When je click checkout
    And je saisi Firstname "Hela"
    And je saisi Lastname "S"
    And je saisi Codepostal "34000"
    And je click Continue
    And l'utilisateur force la navigation vers la page de "Login"
    And lutilisateur tente daccéder directement à l URL de la page Products sans se reconnecter
    Then l accès est refusé et l utilisateur est maintenu sur la page de Login


