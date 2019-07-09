**GitHub Repo Stats**

To run the program
1) Clone the repo.
2) `. ./run.sh --org-name=netflix --top-n=10`

Note - 
1) If the rate limit has reached the tool would not provide any results.
Passing a github token can increase the number of request allowed per hour. 
Token can be set as the property `github-token` in the `application.properties`
2) If the value of N ('--top-n' is too large, the response time will suffer). 
Try to keep it under 1000.