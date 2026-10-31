## Sprint 0 : Front controller
- creation de FrontController
- creation du fonction processRequest
- apple de process request avec doGet et doPost

## Sprint 1 : Annotation
- creation de l'annotation "Controller"
- creation des classes utils :
    - UtilAnalyser : fonction chercher tous les classes dans le projet (selon package ou non)
    - ScanAnnotation : fonction trouver tous les classes qui contient les annotations à chercher
- ajout de l'attribut classes dans FrontController
- Overide de la fonction init() pour charger tous les classes lors du lancement
- Modification de processRequest pour afficher tous les classes trouver

##  Sprint 2 : Url Mapping
- creation de l'annotation "UrlMaping"
- ajout de l'argument url dans l'annotation
- Ajout de fonction dans ScanAnnotation :
    - fonction getMethodWithAnnotation : fonction qui retourne tous les methodes d'une classe qui contient une annotation
- creation d'un classe Mapping qui contient class et method 
- ajout d'un attribut HashMap mapping dans FrontController :
    - clé : url de la map (String)
    - value : la nouvelle classe Mapping
- creation d'une methode initMapping dans FrontController pour remplir le hashMap
    - parcour des listes de classes initialiser
    - appel de la fonction getMethodWithAnnotation
    - creation des mapping 
- appel de initMapping dans init
- changement de processRequest 
    - afficher une erreur en cas de mapping introuvable et afficher tous les url disponible
    - afficher l'url, sa classe et methode correspondant si url dispo
- Refactor : creation des variables static final pour garder les class des annotation


## Sprint 3 

### Sprint 3 : suite de Sprint 2
Prise en charge des types de methode http (GET/POST)
- Creation d'une classe UrlMethode :
    - attributs :
        - url (String)
        - methode (String ou creation d'un Enum java) GET/POST
    - Override de la fonction equals pour des futurs comparaison
- Ajout d'un nouveau attribut dans l'annotation UrlMapping : method pour definir le methode http
- Changement de la clé de l'ancien map par la nouvelle classe
- Mise à jour de la fonction initMApping

### Sprint 3-Bis
- Invocation de la methode qui contient le l'url par reflexion

## Sprint 4-Special
- Creation de la classe FrontControllerListner.java :
    - implement : ServletContextListner
    - Override de la fonction ContextInitialized
    - transfert des fonctions de l'ancien init pour initialiser 
    - envoye du mapping gener par ServletContext
- Recupaeration des maps depuis le FrontController par Context

## Sprint 5 :
### Sprint 5-Support de vue ModelAndView
- Creation de la classe ModelAndView :
    - attributs : String view, Map<String, Object> data
    - fonction addAttribut(clé, object) pour ajouter des élements à la map
    - constructeur avec le parametre view
    - fonction setview
- Modification de ProcessRequest sur l'invocation:
    - exception si le type de retour n'est pas ModelAndView
    - caster le resultat de retour 
    - creation d'un fonction pour ajouter data du model dans le request SErvlet : addArgToRequest(req, map)
    - creation d'un dispatcher qui envoye vers la page correspondant au vu

### Sprint 5-bis connection à Spring
- dans frontcontrollerlistner :
    - ajout d'un variable static final SPRING_ROOT
    - mettre valeur de SPRING_ROOT à "org.springframework.web.context.WebApplicationContext.ROOT"
    - envoyer un attribut nommer springcontext qui contient la valeur de SPRING_ROOT dans le context :
        servletContext.setAttribute("springContext", servletContext.getAttribute(SPRING_ROOT))
- creation d'un classe Util :
    - ajout de la fonction ststic boolean haveParameter(Method methode, Class<?> param) qui verifie si une methode à la classe param comme parametre
- dans frontcontroller :
    - recuperer le springcontext depuis le context et caster en WebApplicationContext
    - changer l'invocation de la methode :
        - verifier si la methode attend une parmetre WebApplication avec la fonction haveParam()
            - si oui : 
                - si springcontext == null : throw excepltion pas de springcontext
                - invoker la methode en mettant en argument le springcontext :
                    - result = (ModelAndView) method.invoke(obj,  springContext);
            - sinon : invocation simple comme avant

### Sprint 6 : ajout de annotation API et execution
- creation d'un nouveau annotation appeler APIREST, APIWEB
- dans frontcontroller :
    - avant execution du dispat
        - si la classe de la methode est contient la nouvelle annotation  :
            - retourne une application json
            - 2 cas, type de retour de la methode :
                - String (dejà du json): on retourne tout de suite la string dans l'app json
                - Autre que String (ou pas du json) : on appel une fonction toJson pour convertir l'objet en Json (manuel ou bibliotheque existant)
            - retourner un application json
        - sinon :
            - on garde l'ancienne execution 

### Sprint 7 : binding
?? Donnée venant de formulaire de vue envoyer au controller et à renvoyer à une autre vue via get ou post

!! Convention : spring context toujours arg1
- Dans frontController :
    - modifier les methodes d'invocations verifier si la methode est :
        -  si sans parametres :
            - methode simple
        - si avec parametre :
            - declarer une liste des parametre a utiliser pour l'execution : listeArg
            - si avec spring :
                - ajouter le contextespring à listeArg
            - boucler liste des parametre à partir de 2e arg :
                - prendre la valeur du parametre avec le meme nom envoyer avec la requete (request param)
                - si exite mettre dans listArg
                - sinon mettre valeur par defaut ou null[]
            
### Sprint 7 bis: 
la même que 7 mais avec des objets

### Sprint 7 améliore : 
- ajout de reccurtion exemple, objet personne: adresse, nom -> adresse (lot, parcelle);
- ajout de possibilité de plusieurs mêmes objets, on doit connaître la provenance de leurs donnés, exemple : 2 personnes , touts deux des objets du types personnes, on doit connaître provenance leur donnés , inpunt (nom1,age1) (nom2,age2) et on doit savoir à qui appartient age1, mais pas de (nom2,age1) ou de (nom1,age2)
- on peut inserer des objets et des non objet en même temps (7 et 7#bis en même temps)

### Sprint 8 : upload 

### Sprint 9 : session
- On va mettre les sessions utilisateur dans le controller du développeur 
- En utilisant des annotations 
- injection

### Sprint 10 : autorisation
- il y a des méthodes d'actions dans le controller, côté développeur, on a besoin d'être authentifié (login + password) pour les réaliser

#### #bis 
- certaines actions nécessitent des rôles spécifiques pour être executé  

### Sprint 11 : singleton
- Un seul controller (FrontController), une seule instance de controller, on se débrouille pour la démo

- Crée un contrôleur, mets un `sysout` à l'intérieur de son constructeur de sorte que le `sysout` ne sorte qu'une seule fois. Lorsque l'on appelle le contrôleur pour la première fois, c'est là qu'il est instancié, et on garde cette instance dans une map.
