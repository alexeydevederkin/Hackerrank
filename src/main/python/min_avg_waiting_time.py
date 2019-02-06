#!/bin/python3

'''
Tieu owns a pizza restaurant and he manages it in his own way. While in a normal restaurant, 
a customer is served by following the first-come, first-served rule, Tieu simply minimizes 
the average waiting time of his customers. So he gets to decide who is served first, 
regardless of how sooner or later a person comes.

Different kinds of pizzas take different amounts of time to cook. Also, once he starts cooking a pizza, 
he cannot cook another pizza until the first pizza is completely cooked. Let's say we have three customers 
who come at time t=0, t=1, & t=2 respectively, and the time needed to cook their pizzas is 3, 9, & 6 respectively. 
If Tieu applies first-come, first-served rule, then the waiting time of three customers is 3, 11, & 16 respectively. 
The average waiting time in this case is (3 + 11 + 16) / 3 = 10. This is not an optimized solution. 
After serving the first customer at time t=3, Tieu can choose to serve the third customer. 
In that case, the waiting time will be 3, 7, & 17 respectively. 
Hence the average waiting time is (3 + 7 + 17) / 3 = 9.

Help Tieu achieve the minimum average waiting time. For the sake of simplicity, 
just find the integer part of the minimum average waiting time.

input:
    3
    0 3
    1 9
    2 6

output:
    9
'''

from queue import PriorityQueue


def solve():
    queue_arrival = PriorityQueue()
    queue_cook_time = PriorityQueue()
    end_time = 0
    sum_time = 0

    num = int(input())

    # parse all data and push it in queue_arrival
    for i in range(num):
        arrival, cook_time = [int(x) for x in input().split()]
        queue_arrival.put((arrival, cook_time))

    while True:
        next_arrival = 0

        # pop all arrived by end_time from queue_arrival
        # push them into queue_cook_time
        if not queue_arrival.empty():
            next_arrival = queue_arrival.queue[0][0]
            while not queue_arrival.empty() and end_time >= queue_arrival.queue[0][0]:
                arrival, cook_time = queue_arrival.get()
                queue_cook_time.put((cook_time, arrival))

        # pop 1 item from queue_cook_time or move to next_arrival or break
        if queue_cook_time.empty():
            if queue_arrival.empty():
                break
            end_time = next_arrival
            continue
        else:
            cook_time, arrival = queue_cook_time.get()

        # add waiting time for it to sum_time
        # update end_time
        end_time += cook_time
        sum_time += end_time - arrival

    average_time = sum_time // num
    print(average_time)


if __name__ == '__main__':
    solve()