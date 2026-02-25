# Android Image Gallery App

אפליקציית גלריה לאנדרואיד המאפשרת בחירה והצגה של תמונות.

## תכונות

- **בחירת תמונות מרובות** - בחירת מספר תמונות בו-זמנית מהגלריה
- **תצוגת גריד** - הצגת התמונות ב-3 עמודות
- **מחיקת תמונה בודדת** - כפתור X על כל תמונה להסרתה
- **נקה הכל** - ניקוי כל הגלריה בלחיצה אחת
- **מסך מלא** - לחיצה על תמונה פותחת אותה במסך מלא
- **זום** - פנץ' לזום וגרירה לניווט
- **דאבל טאפ** - לחיצה כפולה לאיפוס הזום
- **תמיכה ב-RTL** - ממשק בעברית

## מבנה הפרויקט

```
app/src/main/
├── java/com/example/imagegallery/
│   ├── MainActivity.kt              # מסך ראשי עם גלריה
│   ├── ImageGalleryAdapter.kt       # אדפטר RecyclerView לתמונות
│   └── FullScreenImageActivity.kt   # מסך תמונה מלאה עם זום
├── res/
│   ├── layout/
│   │   ├── activity_main.xml        # Layout מסך ראשי
│   │   ├── item_image.xml           # Layout פריט תמונה בגריד
│   │   └── activity_full_screen_image.xml  # Layout מסך מלא
│   ├── drawable/
│   │   ├── ic_close.xml             # אייקון סגירה
│   │   ├── ic_arrow_back.xml        # אייקון חזרה
│   │   ├── ic_photo_library.xml     # אייקון גלריה ריקה
│   │   └── circle_background.xml   # רקע עגול לכפתורים
│   └── values/
│       ├── colors.xml               # צבעים
│       ├── strings.xml              # מחרוזות
│       └── themes.xml               # ערכות נושא
└── AndroidManifest.xml
```

## הרשאות

- `READ_MEDIA_IMAGES` - Android 13+ (API 33+)
- `READ_EXTERNAL_STORAGE` - Android 12 ומטה (עד API 32)

## דרישות

- minSdk: 21 (Android 5.0 Lollipop)
- targetSdk: 34 (Android 14)
- Kotlin 1.9.22
- Android Gradle Plugin 8.2.0

## ספריות

- **Glide 4.16.0** - טעינת תמונות יעילה עם cache
- **Material Components** - עיצוב Material Design
- **AndroidX** - ספריות תאימות

## הפעלה

1. פתח ב-Android Studio
2. חכה לסנכרון Gradle
3. הרץ על אמולטור או מכשיר פיזי
