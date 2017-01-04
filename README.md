# Consumer-Driven Contract Testing

Consumer-Driven contracts provide a mechanism for API consumers to define the interface they expect an API producer to implement. This is in contrast to a typical producer/consumer scenario in which the producer defines the API and the consumer is responsible for learning the API and adapting to changes.

Consider the case of the curious "UserAPI". In the beginning, the "UserAPI" provided consumers the ability to retrieve users via a REST call: `GET /users`.

There came such a day when a product manager came along and asked the development team to expose user profiles in a new and just dandy way. One might call it splendiferous. They probably wouldn't.

Anyhoo, the development team happily went about crafting the new profile page. Whilst developing the just dandy new features, they came to realize that the UserAPI did not, in fact, provide user names! To make matters worse, the UI development team did not own the UserAPI and therefore could not conjure forth the user names they sought.

Enter contracts -

Scrambling to implement the feature, the UI development team crafted a mechanism for declaring their desires to the UserAPI team: 🎉**CONTRACTS**🎉. Into a new repository, the UI team placed a contract the defined the current interface of the UserAPI. This would henceforth be known as their "initial commit".

To the repository, they added a series of scripts for generating a mock version of the UserAPI based on the contract. With the contract in place, and the "fakeout" scripted, the UI team was thence capable of writing acceptance tests without the overhead of the actual UserAPI. By adding the user's name to the their UI, and adding the `name` field to the contract, the UI team was able to call their feature "complete." They pushed the work to a branch (in both their repository and the contract repository) and opened a Pull Request in the contract repository.

But what of our brave API team? What became of them? From the wilds they emerged to find **LO** a contract test branch hath failed! 'Twas now upon the API team to turn this failure into a success. It was a simple matter of code. Having made the appropriate modifications resulting in the passage of the failing test, thusly upholding their responsibilities pursuant to the contract set forth by the UI team, the API team then did proceed to merge the contract pull request into the master branch of said repository.

And thus, with the UserAPI teams' tests passing and the contracts enforced, the UI team merged their work, the product manager (remember them?) was pleased, and all was right with the world.
