# Spam detector.

## Purpose
Get familiar with coroutines concept. Understand how to organize coroutines and how to do work in parallel and asynchronously.

## Introduction
Let’s imagine that we need to implement spam detection in text.
The program will assume that particular text is spam if it contains the following words: “spam”, “advertisement”.
However, we have to implement a program in such a way that it will process text in parallel.
The program should create a separate coroutine for each`n`of text lines (where`n` can be configured).
Also, if one of the coroutines finds one of the spam markers, all detection coroutines should stop execution.
As a result, such detection should return that the text contains spam, because at least one of the parts contains spam.

## Task description
Your task is to implement three classes:
* LetterProcessor – the class will split the text into several parts.
* WordSpecificSearchSpamDetector – the class will process text and determine whether the text contains spam. It will be determined based on the markers in the text.
* ConcurrentSpamDetector – the class will process the text in two phases:
    1. Split the text into several parts using LetterProcessor.
    2. Process each part using WordSpecificSearchSpamDetector.
    3. Join all results and return `true` for spam detection if at least one part contains spam.

