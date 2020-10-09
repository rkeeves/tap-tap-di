# tap-tap

## Overview

A simplistic di for learning purposes, inspired by [Double Tap](https://youtu.be/JApznst0LFg?t=62).

It supports only the following simple features:

- allows you to cache instances and use them like they were singletons
- figures out dependencies for autowiring purposes (in a really dumb, naive, and stratight forward way)

Please keep in mind, that the whole point of this is to learn new concepts not to invent the wheel again.

It currently doesn't read byte code ( [ASM](https://asm.ow2.io/), [Javassist](https://www.javassist.org/), [Reflections](https://github.com/ronmamo/reflections) etc.), aka it cannot scan your packages.