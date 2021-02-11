# Spam detector.

## Purpose
Get familiar with coroutines concept. Understand how to organize coroutines and how to do work in parallel and asynchronously.

## Introduction
Let’s imagine that we need to implement spam detection in text.
Program will assume that particular text is spam if it contains following words: “spam”, “advertisement”.
However, we have to implement a program is such a way that it will process text in parallel.
Program should create separate coroutine for each n lines of text (where n can be configured).
Also, if one of the coroutines find one of the spam markers, all detection coroutines should stop execution.
As a result, such detection should return that text contains spam because at least one of the parts contains spam.

## Task description
Your task is to implement three classes:
* LetterProcessor – the class will split text into several parts
* WordSpecificSeachSpamDetector – the class will process text and and determine whether or not text contains spam. It will be determined based on the markers in the text
* ConcurrentSpamDetector – the class will process text in two phases:
    1. Split text into several parts using LetterProcessor
    2. Process each part using WordSpecificSearchSpamDetector
    3. Join all results and return true for spam detection if at least one part contains spam

