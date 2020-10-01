pipeline {

		agent {
	        label 'master'
	    }
	
		triggers {
        	pollSCM('0-59/1 * * * *')
    	}
    	
		stages{
		
	        stage("Compilation du code source")	{
	            steps	{
	            bat "mvn compile "
	            }
	        }
	        stage("Compilation du code des tests  ")	{
	            steps	{
	            bat "mvn test-compile  "
	            
	            
	            }
	            
	       }
	       
	       stage("Tests unitaires avec Junit")	{
	            steps	{
	            bat "mvn test -P unittests"
	            
	            
	            }     
	            
	        }
	        
	        
	        stage("Tests d'intégration avec Failsafe")	{
	            steps	{
	            bat "mvn package -P inttests"
	            
	            
	            }     
	            
	        }
	        
	         stage("Code coverage. Le score minimum de couverture des lignes = 75%")	{
	            steps	{
	            bat " mvn test jacoco:check jacoco:report"
	      		
	      		
	            publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-business/target/site/jacoco',
							reportFiles:	'index.html',
							reportName:	"myerp-business coverage report"
							])
				
				
				
	            publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-model/target/site/jacoco',
							reportFiles:	'index.html',
							reportName:	"myerp-model coverage report"
							])
				
				 publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-technical/target/site/jacoco',
							reportFiles:	'index.html',
							reportName:	"myerp-technical coverage report"
							])
				
				
				
	            bat "mvn verify";
	            }
	            post {
	                always {
	                    junit '**/target/surefire-reports/*.xml'
	                    step( [ 
						  $class: 'JacocoPublisher'
						])
	                    }
	            	}
	        }
			stage("Packaging de l'application")	{
		            steps	{
		            bat "mvn package -DskipTests"
		            }
		        }
	
	    }
	        
	        
	 } 