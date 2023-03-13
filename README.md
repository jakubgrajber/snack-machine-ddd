# Domain-Driven Design in practice
## Snack Machine bounded context

This is a project I developed during the following course: 
https://www.pluralsight.com/courses/domain-driven-design-in-practice

# Snack Machine - bounded context
## Problem description - requirements

1. The <code>SnackMachine</code> should be able to work with coins and notes:
   - insert money into the Machine
   - return the inserted money back
   - buy a snack and return a change

2. The <code>SnackMachine</code> should have 3 slots each of which can contain several snacks of the same type and price.
3. Check if inserted money is enough and the slot isn't empty.
4. Check if there's enough change.
5. Always return coins and notes with the largest denomination.
