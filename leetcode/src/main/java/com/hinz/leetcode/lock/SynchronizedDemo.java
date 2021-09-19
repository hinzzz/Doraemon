package com.hinz.leetcode.lock;

public class SynchronizedDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SynchronizedDemo().init();
	}

	private void init(){
		final Outputer outputer = new Outputer();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.output("aaaaaaaaa");
				}

			}
		}).start();

		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.output3("bbbbbbbbbbbbbbbbb");
				}

			}
		}).start();
	}

	static class Outputer{
		public void output(String name){
			synchronized (Outputer.class)
			{
				for(int i=0;i<name.length();i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		public synchronized void output2(String name){
			for(int i=0;i<name.length();i++){
					System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		public static synchronized void output3(String name){
			for(int i=0;i<name.length();i++){
					System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		public  void output4(String name){
			synchronized (this) {
				for (int i = 0; i < name.length(); i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
	}
}
