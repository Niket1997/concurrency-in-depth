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

func main() {
	// 26358746
	start := time.Now()

	var wg sync.WaitGroup
	nStart := 3
	batchSize := int(float64(MAX_INT) / float64(CONCURRENCY))

	for i := 0; i < CONCURRENCY; i++ {
		wg.Add(1)
		go doWork(strconv.Itoa(i), &wg, nStart, nStart+batchSize)
		nStart += batchSize
	}

	wg.Wait()
	fmt.Println("checking till", MAX_INT, "found", totalPrimeNumbers+1,
		"prime numbers. took", time.Since(start))
}

func doWork(name string, wg *sync.WaitGroup, nStart int, nEnd int) {
	defer wg.Done()

	start := time.Now()

	for i := nStart; i < nEnd; i++ {
		checkPrime(i)
	}

	fmt.Printf("thread %s [%d, %d] completed in %s\n", name, nStart, nEnd, time.Since(start))
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
