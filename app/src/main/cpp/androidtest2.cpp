#include "randomData.h"
#include <jni.h>

extern "C" JNIEXPORT jfloatArray JNICALL
Java_com_example_androidtest2_joinedshapes_JoinedShapesView_passRandData(JNIEnv* env, jclass clazz, jint id) {
   // return reinterpret_cast<jfloatArray>(FloatArrayWrapper(id).floatArray);
    jfloatArray result;
    result = (*env).NewFloatArray(ovalRandLength);
    switch (id) {
        case 0: //ovalX
            (*env).SetFloatArrayRegion(result, 0, ovalRandLength, ovalX);
           // return reinterpret_cast<jfloatArray>(ovalX);
            break;
        case 1: //ovalY
            (*env).SetFloatArrayRegion(result, 0, ovalRandLength, ovalY);
          //  return reinterpret_cast<jfloatArray>(ovalY);
            break;
        case 2: //ovalW
            (*env).SetFloatArrayRegion(result, 0, ovalRandLength, ovalW);
         //   return reinterpret_cast<jfloatArray>(ovalW);
            break;
        case 3: //ovalH
            (*env).SetFloatArrayRegion(result, 0, ovalRandLength, ovalH);
         //   return reinterpret_cast<jfloatArray>(ovalH);
            break;
        case 4: //quadX1
            (*env).SetFloatArrayRegion(result, 0, quadRandLength, quadX1);
            break;
        case 5: //quadY1
            (*env).SetFloatArrayRegion(result, 0, quadRandLength, quadY1);
            break;
        case 6: //quadX2
            (*env).SetFloatArrayRegion(result, 0, quadRandLength, quadX2);
            break;
        case 7: //quadY2
            (*env).SetFloatArrayRegion(result, 0, quadRandLength, quadY2);
            break;
        case 8: //quadX3
            (*env).SetFloatArrayRegion(result, 0, quadRandLength, quadX3);
            break;
        case 9: //quadY3
            (*env).SetFloatArrayRegion(result, 0, quadRandLength, quadY3);
            break;
    }
    return result;
}


/*extern "C" JNIEXPORT void JNICALL
Java_com_example_androidtest2_MyNativeModule_releaseFloatArrayWrapper(JNIEnv* env, jclass clazz, jlong ptr) {
    delete reinterpret_cast<FloatArrayWrapper*>(ptr);
}*/

extern "C" JNIEXPORT jint JNICALL
Java_com_example_androidtest2_joinedshapes_JoinedShapesView_getArraySize(JNIEnv* env, jclass clazz, jfloatArray ptr) {
   // FloatArrayWrapper* wrapper = reinterpret_cast<FloatArrayWrapper*>(ptr);
   // return wrapper->getArraySize();
   return ovalRandLength;
}

/*
extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_androidtest2_MyNativeModule_getElementByIndex(JNIEnv* env, jclass clazz, jlong ptr, jint index) {
    FloatArrayWrapper* wrapper = reinterpret_cast<FloatArrayWrapper*>(ptr);
    return wrapper->getElementByIndex(index);
}*/
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_example_androidtest2_joinedshapes_JoinedShapesView_passRandRGB(JNIEnv *env, jclass clazz,
                                                                        jint id) {
    // TODO: implement passRandRGB()
    jintArray result;
    result = (*env).NewIntArray(ovalRandLength);
    switch (id) {
        case 0: //R
            (*env).SetIntArrayRegion(result, 0, ovalRandLength, paintR);
            // return reinterpret_cast<jfloatArray>(ovalX);
            break;
        case 1: //G
            (*env).SetIntArrayRegion(result, 0, ovalRandLength, paintG);
            //  return reinterpret_cast<jfloatArray>(ovalY);
            break;
        case 2: //B
            (*env).SetIntArrayRegion(result, 0, ovalRandLength, paintB);
            //   return reinterpret_cast<jfloatArray>(ovalW);
            break;
    }
    return result;
}