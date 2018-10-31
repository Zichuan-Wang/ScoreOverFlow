#!/bin/sh
#
# Script to set pre-commit git-hook as a synlink to the actual hook script which
# is maintained within the repo proper.

GITHOOKS_DIR=$(git rev-parse --show-toplevel)/.git/hooks
REPOHOOKS_DIR=$(git rev-parse --show-toplevel)/scripts/git-hooks

# If something exists, back it up
if [ -e "$GITHOOKS_DIR/pre-commit" ]
then
    mv "$GITHOOKS_DIR/pre-commit" "$GITHOOKS_DIR/pre-commit.old"
fi

ln -s -f "$REPOHOOKS_DIR/pre-commit" "$GITHOOKS_DIR/pre-commit"