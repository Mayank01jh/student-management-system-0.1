#!/bin/bash
echo "========================================="
echo "  Student Management System - DSA Edition"
echo "========================================="
echo ""
echo "Opening in your default browser..."
echo ""

# Get the directory of this script
DIR="$(cd "$(dirname "$0")" && pwd)"

# Open index.html based on OS
if [[ "$OSTYPE" == "darwin"* ]]; then
    open "$DIR/index.html"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    xdg-open "$DIR/index.html"
else
    echo "Please manually open: $DIR/index.html"
fi

echo "If browser didn't open, manually open: index.html"
