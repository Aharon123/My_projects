// Get elements
const textArea = document.getElementById('textArea');
const clearBtn = document.getElementById('clearBtn');
const sampleBtn = document.getElementById('sampleBtn');
const charCount = document.getElementById('charCount');
const wordCount = document.getElementById('wordCount');
const lineCount = document.getElementById('lineCount');

// Sample text in Hebrew
const sampleText = `זה טקסט לדוגמה

יש כאן כמה שורות של טקסט כדי שתוכל לראות איך האפליקציה עובדת.

אתה יכול להקליד טקסט משלך, ללחוץ על "נקה טקסט" כדי למחוק הכל,
או ללחוץ על "טעון דוגמה" כדי לטעון את הטקסט הזה.

בשורה הקטנה בתחתית אתה יכול לראות:
- כמה תווים כתבת
- כמה מילים כתבת
- כמה שורות כתבת

בהצלחה! 🎉`;

// Update counters
function updateCounters() {
    const text = textArea.value;

    // Count characters
    const characters = text.length;
    charCount.textContent = `תווים: ${characters}`;

    // Count words
    const words = text.trim() === '' ? 0 : text.trim().split(/\s+/).length;
    wordCount.textContent = `מילים: ${words}`;

    // Count lines
    const lines = text === '' ? 1 : text.split('\n').length;
    lineCount.textContent = `שורות: ${lines}`;
}

// Clear text
clearBtn.addEventListener('click', () => {
    if (textArea.value.trim() !== '') {
        if (confirm('האם אתה בטוח שאתה רוצה למחוק את כל הטקסט?')) {
            textArea.value = '';
            updateCounters();
            textArea.focus();
        }
    } else {
        alert('אין טקסט למחיקה!');
    }
});

// Load sample text
sampleBtn.addEventListener('click', () => {
    textArea.value = sampleText;
    updateCounters();
    textArea.focus();
});

// Update counters when typing
textArea.addEventListener('input', updateCounters);

// Initialize counters
updateCounters();

// Focus on textarea when page loads
window.addEventListener('load', () => {
    textArea.focus();
});
