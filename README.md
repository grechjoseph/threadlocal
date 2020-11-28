<h1>Inheritable Threadlocal</h1>

This example project demonstrates how an Inheritable Threadlocal variable persists in child threads even when this is cleared in the Main thread. It does not affect/change the Threadlocal value in the child threads.

For example:
1. Main Thread initializes a Threadlocal context.
2. Main Thread calls a Child Thread.
3. Child Thread starts executing.
4. Main Thread clears/changes the Threadlocal context.
5. RESULT: Threadlocal context is not changed in the Child thread.

In other words, an InheritableThreadLocal only serves as an initial value for child threads, and offers no additional synchronization of values between parent and child threads.