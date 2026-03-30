#!/bin/bash
# ─────────────────────────────────────────────
# Student Management System — Build & Run Script
# ─────────────────────────────────────────────

set -e

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC_DIR="$PROJECT_DIR"
OUT_DIR="$PROJECT_DIR/out"
LIB_DIR="$PROJECT_DIR/lib"

echo "╔══════════════════════════════════════╗"
echo "║   SMS Build Script                   ║"
echo "╚══════════════════════════════════════╝"

# Create output directories
mkdir -p "$OUT_DIR" "$LIB_DIR"

# ── Download JUnit 5 if not present ──────────────
JUNIT_JAR="$LIB_DIR/junit-platform-console-standalone-1.10.0.jar"
if [ ! -f "$JUNIT_JAR" ]; then
    echo "Downloading JUnit 5..."
    curl -sL "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar" \
         -o "$JUNIT_JAR"
    echo "✓ JUnit downloaded"
fi

# ── Compile main sources ──────────────────────────
echo ""
echo "Compiling main sources..."
javac -d "$OUT_DIR" \
    "$SRC_DIR/model/Student.java" \
    "$SRC_DIR/ds/StudentLinkedList.java" \
    "$SRC_DIR/ds/StudentBST.java" \
    "$SRC_DIR/ds/ActionStack.java" \
    "$SRC_DIR/module/StudentRegistry.java" \
    "$SRC_DIR/module/GradeBook.java" \
    "$SRC_DIR/module/AttendanceTracker.java" \
    "$SRC_DIR/module/SearchSort.java" \
    "$SRC_DIR/util/FileManager.java" \
    "$SRC_DIR/Main.java"
echo "✓ Main sources compiled"

# ── Compile test sources ──────────────────────────
echo "Compiling test sources..."
javac -cp "$OUT_DIR:$JUNIT_JAR" -d "$OUT_DIR" \
    "$SRC_DIR/test/StudentLinkedListTest.java" \
    "$SRC_DIR/test/StudentBSTTest.java" \
    "$SRC_DIR/test/ActionStackTest.java" \
    "$SRC_DIR/test/SearchSortTest.java" \
    "$SRC_DIR/test/StudentRegistryTest.java" \
    "$SRC_DIR/test/GradeBookTest.java"
echo "✓ Test sources compiled"

# ── Run tests ─────────────────────────────────────
if [ "${1}" == "--test" ]; then
    echo ""
    echo "Running JUnit tests..."
    java -jar "$JUNIT_JAR" \
        --classpath "$OUT_DIR" \
        --scan-classpath \
        --details verbose
    exit 0
fi

# ── Run the app ───────────────────────────────────
echo ""
echo "Starting Student Management System..."
echo ""
java -cp "$OUT_DIR" Main
