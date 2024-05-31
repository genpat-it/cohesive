#!/bin/bash

# Set the current directory
DIR="."

# Iterate over each .tgz and .tar file in the directory
for file in "$DIR"/*.{tgz,tar}; do
  # Check if the file exists to handle the case when no files are found
  if [[ -f "$file" ]]; then
    # Extract the base name of the file without extension
    base_name=$(basename "$file")
    base_name="${base_name%.*}"

    # Create a directory with the base name
    mkdir -p "$DIR/$base_name"

    # Extract the file into the respective directory
    if [[ "$file" == *.tgz ]]; then
      tar -xvzf "$file" -C "$DIR/$base_name"
    elif [[ "$file" == *.tar ]]; then
      tar -xvf "$file" -C "$DIR/$base_name"
    fi

    # Delete the original archive file
    rm "$file"
  fi
done

echo "Extraction and cleanup completed."
