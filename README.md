# Week 8 Practice Assignment

Five Java command-line solutions demonstrating polymorphism: each payment method,
library item, delivery service, question type, and transport type implements its
own behavior while the main program processes a collection uniformly.

## Solutions

| Folder | Program | Description |
| --- | --- | --- |
| `Problem1` | `PaymentSystem` | Applies processing fees to card, wallet, and bank transfer transactions. |
| `Problem2` | `LibraryDueDateCalculator` | Calculates due dates from the fixed date 2023-10-26. |
| `Problem3` | `DeliveryFeeCalculator` | Calculates delivery fees from delivery type, weight, distance, and customs fee. |
| `Problem4` | `ExaminationGrader` | Grades objective and essay questions. |
| `Problem5` | `PublicTransportFareCalculator` | Calculates bus, train, and metro fares. |

## Compile and run

Run these commands from the corresponding problem folder:

```text
javac PaymentSystem.java
java PaymentSystem
```

Replace `PaymentSystem` with the Java filename's class name for the other
problems. Each program reads the problem's specified input from standard input.
The `.class` files are included alongside the Java sources.

For Problem 3, the implementation follows the fee formulas in the assignment.
The supplied international-delivery sample does not match those formulas: its
input calculates to 145.00, not 155.00, and the corresponding total is 184.00.
