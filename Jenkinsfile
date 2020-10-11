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
	       
	       stage("Tests unitaires")	{
	            steps	{
	            bat "mvn test -P unittests"
	            
	            
	            }     
	            
	        }
	        
	        
	        stage("Tests d'intégration")	{
	            steps	{
	            bat "mvn integration-test -P inttests"
	            
	            
	            }     
	            
	        }
	        
	         stage("Code coverage. Le score minimum de couverture des lignes = 75%")	{
	            steps	{
	            bat " mvn test jacoco:check jacoco:report -X"
	      		
	      		
	            publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-business/target/site/jacoco-unit-test-coverage-report',
							reportFiles:	'index.html',
							reportName:	"myerp-business coverage UT report"
							reportTitles: "myerp-business coverage UT report"
							])
				
				publishHTML	(target:	[
							allowMissing: false,
							allowScripts: true,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-business/target/site/jacoco-integration-test-coverage-report',
							reportFiles:	'index.html',
							reportName:	"myerp-business coverage IT report"
							])
							
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
				
				publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-consumer/target/site/jacoco',
							reportFiles:	'index.html',
							reportName:	"myerp-consumer coverage report"
							])
				
	            bat "mvn verify";
	            }
	            post {
	                always {
	                    junit '**/target/surefire-reports/*.xml'
	                    junit '**/target/failsafe-reports/*.xml'
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