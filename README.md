# Student Management System — DSA Edition

A fully functional Student Management System built with core Data Structures & Algorithms.

## How to Run

### Windows
Double-click `run.bat`
OR right-click `index.html` → Open with → Chrome / Firefox / Edge

### Mac
Double-click `run.sh`
OR double-click `index.html`

### Linux
```bash
chmod +x run.sh
./run.sh
```
OR open `index.html` in any browser.

---

## Features

| Feature            | Data Structure Used         | Time Complexity |
|--------------------|-----------------------------|-----------------|
| Add Student        | Linked List (insert front)  | O(1)            |
| Remove Student     | Linked List (traversal)     | O(n)            |
| Find Student       | Linked List (traversal)     | O(n)            |
| GPA Leaderboard    | BST (in-order traversal)    | O(n)            |
| Update GPA         | HashMap + BST delete/insert | O(log n)        |
| Sort by Name/GPA   | Merge Sort                  | O(n log n)      |
| Search by Roll#    | Binary Search               | O(log n)        |
| Search by Name     | Linear Search               | O(n)            |
| Attendance         | Queue (FIFO)                | O(1) enqueue    |
| Undo Last Action   | Stack (LIFO)                | O(1)            |

---

## Project Structure

```
SMS_Project/
├── index.html        ← Main app (open this)
├── run.bat           ← Windows launcher
├── run.sh            ← Mac/Linux launcher
└── README.md         ← This file
```

---

## Tech Stack
- Pure HTML + CSS + JavaScript (no dependencies, no install needed)
- All DSA implemented from scratch in JS
- Works 100% offline in any modern browser
