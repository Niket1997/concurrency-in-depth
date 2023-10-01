package main

import (
	"fmt"
	"math"
	"strconv"
	"sync"
	"sync/atomic"
	"time"
)

var MAX_INT = 500000000
var totalPrimeNumbers int32 = 0
var CONCURRENCY = 10
var currentNum int32 = 2

func main() {
	start := time.Now()

	var wg sync.WaitGroup

	for i := 0; i < CONCURRENCY; i++ {
		wg.Add(1)
		go doWork(strconv.Itoa(i), &wg)
	}

	wg.Wait()
	fmt.Println("checking till", MAX_INT, "found", totalPrimeNumbers+1,
		"prime numbers. took", time.Since(start))
}

func doWork(name string, wg *sync.WaitGroup) {
	defer wg.Done()
	start := time.Now()

	for {
		x := atomic.AddInt32(&currentNum, 1)
		if x > int32(MAX_INT) {
			break
		}
		checkPrime(int(x))
	}

	fmt.Printf("thread %s completed in %s\n", name, time.Since(start))
}

func checkPrime(num int) {
	if num&1 == 0 {
		return
	}

	for i := 3; i < int(math.Sqrt(float64(num))); i++ {
		if num%i == 0 {
			return
		}
	}

	atomic.AddInt32(&totalPrimeNumbers, 1)
}
