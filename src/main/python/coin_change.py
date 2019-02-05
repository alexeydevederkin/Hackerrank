#!/bin/python3

'''
You are working at the cash counter at a fun-fair, and you have different types of coins available
to you in infinite quantities. The value of each coin is already given. 
Can you determine the number of ways of making change for a particular number of units 
using the given types of coins?

For example, if you have 4 types of coins, and the value of each type is given as 8, 3, 1, 2 respectively, 
you can make change for 3 units in three ways: {1, 1, 1}, {1, 2}, and {3}.

Print a long integer denoting the number of ways we can get a sum of n from the given 
infinite supply of m types of coins.
'''

def make_change(amount, coins, current_coin, cache):
    if (amount, current_coin) in cache:
        return cache[(amount, current_coin)]

    current_coin_idx = coins.index(current_coin)

    if current_coin_idx == len(coins) - 1:
        if amount % current_coin == 0:
            return 1
        else:
            return 0

    next_coin_idx = current_coin_idx + 1
    next_coin = coins[next_coin_idx]

    i = 0
    ways = 0
    while i * current_coin <= amount:
        ways += make_change(amount - i * current_coin, coins, next_coin, cache)
        i += 1

    cache[(amount, current_coin)] = ways
    return ways


def solve():
    amount, types = [int(x) for x in input().split()]
    coins = [int(x) for x in input().split()]

    coins.sort(reverse=True)

    num_of_changes = make_change(amount, coins, coins[0], {})
    print(num_of_changes)


if __name__ == '__main__':
    solve()