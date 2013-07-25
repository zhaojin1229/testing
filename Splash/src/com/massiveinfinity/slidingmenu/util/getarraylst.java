

package com.massiveinfinity.slidingmenu.util;


import java.util.ArrayList;


public class getarraylst {

		 static public ArrayList<String> findIndexes(String content)
			{
				ArrayList<String> index=new ArrayList<String>();
				int contentlen=content.length();
				int len=contentlen;
				
				
				for(;len>0;)
				{
					if(content.contains("src")&&content.contains("jpg"))
					{
						int startcontent=content.indexOf("src");
						int endcontent=content.indexOf("jpg");
						String url=content.substring(startcontent+5,endcontent)+"jpg";

						index.add(url);
						 content=content.substring(endcontent+5);
						
						contentlen=content.length();

						len=contentlen-endcontent;
					}
					else if(content.contains("src")&&content.contains("png"))
					{
						int startcontent=content.indexOf("src");
						int endcontent=content.indexOf("png");
						String url=content.substring(startcontent+5,endcontent)+"png";

						index.add(url);
						 content=content.substring(endcontent+5);
						
						contentlen=content.length();

						len=contentlen-endcontent;
					}
					else
					{
						break;
					}
				}
				return index;
			}
	

		
}




