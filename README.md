Test RocksDB local cache as file 

key value get

Data in cache: 9760282  rows (All is unique)

Data schema



```json
{
    "id": "%VALUE%",
    "name": "%VALUE%", 
    "description": "%VALUE%", 
    "price": "%VALUE%"
}
```

Result:

| Query time | Duration |
| ---------- | -------- |
| 100        | 172.0 ms |
| 1000       | 190.1 ms |
| 10000      | 364.2 ms |
| 100000     | 1.463 s  |
| 1000000    | 5.729 s  |
