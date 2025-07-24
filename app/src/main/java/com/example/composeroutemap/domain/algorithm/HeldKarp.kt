package com.example.composeroutemap.domain.algorithm

/**
 * @param dist  n×n 대칭 거리행렬 (Double, 출발=0)
 * @return      방문 순서 인덱스 리스트 (0 → … → 끝점)
 */
fun heldKarp(dist: Array<DoubleArray>): List<Int> {
    val n = dist.size
    val size = 1 shl (n - 1)
    val dp = Array(n) { DoubleArray(size) { Double.POSITIVE_INFINITY } }
    val parent = Array(n) { IntArray(size) }

    for (i in 1 until n) dp[i][1 shl (i - 1)] = dist[0][i]

    for (mask in 1 until size)
        for (last in 1 until n)
            if (mask and (1 shl (last - 1)) != 0) {
                val prevMask = mask xor (1 shl (last - 1))
                if (prevMask == 0) continue
                for (prev in 1 until n)
                    if (prevMask and (1 shl (prev - 1)) != 0) {
                        val cand = dp[prev][prevMask] + dist[prev][last]
                        if (cand < dp[last][mask]) {
                            dp[last][mask] = cand
                            parent[last][mask] = prev
                        }
                    }
            }

    // 마지막 city = argmin
    val full = size - 1
    var last = (1 until n).minBy { dp[it][full] }
    var mask = full
    val order = ArrayDeque<Int>()
    order.addFirst(last)
    while (mask != 0) {
        val prev = parent[last][mask]
        order.addFirst(prev)
        mask = mask xor (1 shl (last - 1))
        last = prev
    }
    return order.toList()
}