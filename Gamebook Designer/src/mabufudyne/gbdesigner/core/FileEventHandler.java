package mabufudyne.gbdesigner.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mabufudyne.gbdesigner.gui.MainWindow;
/**
 * Handles events related to storage handling such as saving and laoding
 */
public class FileEventHandler {
	
	public static String lastFileLocation;
	public static String lastFileName;
	
	/**
	 * Saves the current Adventure as an *.adv file to the location given by user in selection dialog
	 * @param quickSave - if true, the method doesn't ask for the file destination but rather uses the one that has been used before
	 */
	public static void saveAdventure(boolean quickSave) {
		String savePath = "";
		if (quickSave) savePath = lastFileLocation + lastFileName;
		else savePath = MainWindow.getInstance().invokeSaveDialog(lastFileLocation);

		if (savePath != null) {
			try {
				FileOutputStream fOut = new FileOutputStream(savePath);
				ObjectOutputStream out = new ObjectOutputStream(fOut);
				out.writeObject(StoryPieceManager.getInstance());
				out.close();
				fOut.close();
				
				lastFileLocation = savePath.substring(0, savePath.lastIndexOf(File.separator));
				lastFileName = savePath.substring(savePath.lastIndexOf(File.separator));
				
				System.out.println("Saved to: " + lastFileLocation + lastFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MainWindow.getInstance().buttonCheck();
	}
	
	/**
	 * Loads an Adventure from an existing *.adv file selected by user in selection dialog.
	 */
	public static void loadAdventure() { 
		String loadPath = MainWindow.getInstance().invokeLoadDialog(lastFileLocation);
		if (loadPath != null) {
			try {
				FileInputStream fIn = new FileInputStream(loadPath);
				ObjectInputStream in = new ObjectInputStream(fIn);
				Object loadedObject = in.readObject();
				in.close();
				fIn.close();
				
				if (loadedObject instanceof StoryPieceManager) {
					StoryPieceManager loadedManager = (StoryPieceManager) loadedObject;
					StoryPieceManager.replaceManager(loadedManager);
					MainWindow.getInstance().reloadUI();
				}
				
				MementoManager.getInstance().revertToDefault();
				StoryPieceEventHandler.handleActionAftermath();
				
				lastFileLocation = loadPath.substring(0, loadPath.lastIndexOf(File.separator));
				lastFileName = loadPath.substring(loadPath.lastIndexOf(File.separator));

				System.out.println("Loaded from: " + lastFileLocation + lastFileName);

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MainWindow.getInstance().buttonCheck();
	}
	
	public static void resetPaths() {
		lastFileLocation = lastFileName = null;
	}

}
