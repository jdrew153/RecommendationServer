# RecommendationServer
Mock social media server with recommendation capabilitites. 

Able to create accounts and hash passwords for new users.

Users can create posts and add attributes to their post with respective rankings.

When a user is created, a list of intersts with respective rankings must be passed in. This interest list will be used to 
help guide the recommendation calculations. 

Recommendation endpoints can be used to create a customized feed based on likes, comments, and profile visits.

When a user likes a post, comments, or visits a profile, points will be added to any existing attribute the user is interested in or 
a new attribute will be added to the users interests list.  

Recommendation is mainly calculated through cosine similarity. 

Secured via JWTs.


