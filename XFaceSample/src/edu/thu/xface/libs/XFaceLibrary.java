package edu.thu.xface.libs;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;

import edu.thu.xface.util.CommonUtil;

/**
 * XFace library
 * 
 * @author hujiawei
 * 
 */
public class XFaceLibrary {

	private static final String TAG = "XFaceLibrary";

	// face recognition!!
	private long xface = 0;

	public long trainModel() {
		xface = nativeTrainModel(CommonUtil.FACEDATA_FILEPATH, CommonUtil.ORLFACEDATA_FILEPATH,
				CommonUtil.FACERECMODEL_FILEPATH, CommonUtil.EIGEN_COMPONENT, CommonUtil.EIGEN_THRESHOLD);
		return xface;
	}

	public long initFacerec() {
		xface = nativeInitFacerec(CommonUtil.FACEDATA_FILEPATH, CommonUtil.FACERECMODEL_FILEPATH,
				CommonUtil.EIGEN_COMPONENT, CommonUtil.EIGEN_THRESHOLD);
		return xface;
	}

	public int facerec(Mat mat) {
		return nativeFacerec(CommonUtil.FACERECMODEL_FILEPATH, xface, mat.getNativeObjAddr(), CommonUtil.IMAGE_WIDTH,
				CommonUtil.IMAGE_HEIGHT);
	}

	public int destoryFacerec() {
		return nativeDestoryFacerec(xface);
	}

	private static native long nativeTrainModel(String datapath, String orlpath, String modelpath, int component,
			double threshold);

	private static native long nativeInitFacerec(String datapath, String modelpath, int component, double threshold);

	private static native int nativeFacerec(String modelpath, long xfacerec, long addr, int width, int height);

	private static native int nativeDestoryFacerec(long xfacerec);

	// face detecion!!!
	private long mNativeObj = 0;

	public void initFacedetect(String cascadeName, int minFaceSize) {
		mNativeObj = nativeInitFacedetect(cascadeName, minFaceSize);
	}

	public void startFacedetect() {
		nativeStartFacedetect(mNativeObj);
	}

	public void stopFacedetect() {
		nativeStopFacedetect(mNativeObj);
	}

	public void setMinFaceSize(int size) {
		nativeSetFaceSize(mNativeObj, size);
	}

	public void facedetect(Mat imageGray, MatOfRect faces) {
		nativeFacedetect(mNativeObj, imageGray.getNativeObjAddr(), faces.getNativeObjAddr());
	}

	public void destroryFacedetect() {
		nativeDestroyFacedetect(mNativeObj);
		mNativeObj = 0;
	}

	private static native long nativeInitFacedetect(String cascadeName, int minFaceSize);

	private static native void nativeDestroyFacedetect(long thiz);

	private static native void nativeStartFacedetect(long thiz);

	private static native void nativeStopFacedetect(long thiz);

	private static native void nativeSetFaceSize(long thiz, int size);

	private static native void nativeFacedetect(long thiz, long inputImage, long faces);

}