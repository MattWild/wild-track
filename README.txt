Welcome to Project WildTrack (v1.0)

This tool is for the tracking of testing environments for the L3 Escalations Team at Landis+Gyr. Code development was done 
in the Eclipse IDE with the JavaFX plugin. All other jars are in the /libs folder for the project.

JavaFX Documentation: https://docs.oracle.com/javase/8/javafx/api/toc.htm
JavaFX CSS Documentation: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html

    Eclipse IDE Download Site: http://www.eclipse.org/downloads/packages/installer
    
    e(fx)clipse install steps:
        -- in Eclipse, Help > Install New Software 
        -- in "Work With" find release site for your version of Eclipse (e.g. "Oxygen - http://download.eclipse.org/releases/oxygen")
        -- in filter test type "e(fx)clipse"
        -- under General Pupose Tools click e(fx)clipse
        -- click next and follow steps
        
    EGit install steps:
        -- in Eclipse, Help > Install New Software 
        -- in "Work With" use "http://download.eclipse.org/egit/updates"
        -- check all options
        -- click next and follow steps
        
    Importing Project:
        -- in Eclipse, File > Import > Git > Projects from Git > Clone URI
        -- input URI https://github.com/MattWild/wild-track
        -- ask project owner for user/pass info
        -- select "Import existing Eclipse projects"
        -- select "Working Tree" then Next
        -- import WildTrack
        
    Running WildTrack from Eclipse
        -- in Project Explorer window, go to WildTrack project
        -- navigate to src > application > Main.java
        -- click on green circle with white arrow in toolbar
        
    Exporting Runnable JAR
        -- File > Export > Java > Runnable JAR File
