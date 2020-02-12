-verbose

# Keep name intact, we want to read the code
-dontobfuscate

# Be agressive, remove as much as possible
-optimizationpasses 10

# Keep JVM entry points
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}
