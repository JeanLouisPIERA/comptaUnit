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
	        	
	       
	       stage("Tests unitaires JUNIT")	{
	            steps	{
	            bat "mvn test -P unittests"
	            }     
	        }
	        
	        
	        stage("Tests d'integration FAILSAFE")	{
	            steps	{
	            bat "mvn integration-test -P inttests"
	            }     
	        }
	        
	        stage("Code coverage. Le score minimum de couverture des lignes = 75%")	{
	        
	            steps	{
	            bat " mvn verify jacoco:check jacoco:report -X"
							
				publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-business/target/site/jacoco-merged-test-coverage-report',
							reportFiles:	'index.html',
							reportName:	"myerp-business coverage UT & IT tests report"
							])
							
				publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-consumer/target/site/jacoco-merged-test-coverage-report',
							reportFiles:	'index.html',
							reportName:	"myerp-consumer coverage UT & IT tests report"
							])			
										
				publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-model/target/site/jacoco-merged-test-coverage-report',
							reportFiles:	'index.html',
							reportName:	"myerp-model coverage UT & IT tests report"
							])			
				
				publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-technical/target/site/jacoco-merged-test-coverage-report',
							reportFiles:	'index.html',
							reportName:	"myerp-technical coverage UT & IT tests report"
							])			
				
	           
	            }
	            
	            post {
	                always {
	                    junit '**/target/surefire-reports/*.xml'
	                    junit '**/target/failsafe-reports/*.xml'
	                 
	                    }
	            	}
	        }
	        
			stage("Packaging de l'application")	{
		            steps	{
		            bat "mvn package -DskipTests"
		            }
		        }
	
	    }
	    
	    
	     
	     post{
		    always{
			    cleanWs()
			    }
	    }      
	     
	 } 