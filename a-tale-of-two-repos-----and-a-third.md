# Consumer-Driven Contract Testing

Consider the case of the "UserAPI". Currently the "UserAPI" provides consumers the ability to retrieve users via a REST call: `GET /users`.

One day, the company discovers that their customers would like to view profiles of other users and a development team is created to implement the new functionality. While developing the new profile page, they come to realize that the UserAPI does not provide user names. To make matters worse, the UI development team does not own the UserAPI and therefore cannot show everything the design requires.


## Enter contracts -

The UI team goes to work implementing their feature without the missing data by writing code that adheres to the current UserAPI interface. They codify that expectation in a "contract": a specification of their assumptions about the UserAPI. The contract gets placed into a new repository along with some scripts for API verification and mocking.

The initial contract repository in place, they draft an addendum in a Pull Request to the contract repo and, on a branch of their own, implement the full feature.


## But what of our brave API team? What became of them?

Well, since the contract as defined in the contract repo has changed (albeit on a branch), the API team is notified of a failing test by their continuous integration server. After updating their code to make the contract test succeed, the team can merge the pull request in the contract repo. This can then either notify the UI team of the updated contract or auto-merge the branch in the UI repo.
