# Week-8-Assignment

Five Java programs that demonstrate polymorphism by applying different business
rules to bills, vehicles, rooms, employees, and subscription plans.

## Requirements

- Java 9 or later

## Problems

| Folder | Program | Description |
| --- | --- | --- |
| `Problem1` | `CanteenBillingCounter` | Calculates customer bills and the total collected. |
| `Problem2` | `ParkingChargeCalculator` | Calculates parking charges and the total collected. |
| `Problem3` | `HostelElectricityBill` | Calculates room electricity bills and the total shown. |
| `Problem4` | `FestivalBonusCalculator` | Calculates employee bonuses and the total paid. |
| `Problem5` | `StreamingPlanRenewalReminder` | Calculates subscription renewal dates. |

Each program reads input from standard input and prints the result in the
specified format. The type-specific calculations are implemented by separate
classes selected through a registry.

## Compile and run

Run commands from the repository root. Compile each program before running it:

```sh
javac Problem1/CanteenBillingCounter.java
java -cp Problem1 CanteenBillingCounter

javac Problem2/ParkingChargeCalculator.java
java -cp Problem2 ParkingChargeCalculator

javac Problem3/HostelElectricityBill.java
java -cp Problem3 HostelElectricityBill

javac Problem4/FestivalBonusCalculator.java
java -cp Problem4 FestivalBonusCalculator

javac Problem5/StreamingPlanRenewalReminder.java
java -cp Problem5 StreamingPlanRenewalReminder
```

Provide each problem's input through standard input; the programs do not print
prompts.
