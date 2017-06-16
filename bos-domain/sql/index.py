#!/usr/bin/env python3
# -*- coding: utf-8 -*-

'''
脚本功能：	在目标目录及其子目录下搜索所有的*.html|*.json文件，
			并生成链接保存在目标根目录下的index.html中。
开发初衷：	在easyui的demo目录下生成index.html，便于学习时查阅
使用方法：  python index.py			//默认目标目录为当前工作路径
			python index.py .		//指定目标目录为当前工作路径
			python index.py #目录	//指定目标目录
'''
from sys import argv
import os
import sys

des_filename='index.html'
root="."
if(len(argv)>1):
	root=argv[1]
	if(not os.path.exists(root)):
		print("Error: "+root+"路径有误")
		sys.exit()

sections=[]
		
for dir, subdirs, files in os.walk(root, topdown=True):
	pages=[]
	for file in files:
		if(file.endswith('.html') or file.endswith('.json')):
			p=os.path.join(dir, file).replace(root, '.')
			pages.append(p)
	sections.append(pages)

with open(os.path.join(root, des_filename),'w') as f:
	#写入公共html文件内容
	f.write('<html lang="zh-cn">\n\t<head>\n\t\t<meta http-equiv="content-type" content="text/html; charset=gbk">\n\t</head>\n')
	f.write('<body>\n')
	f.write('	<div style="border:1px #999 solid">\n')
	f.write('		<table border="1"\n')
	f.write('			<thead>\n')
	f.write('				<tr>\n')
	f.write('					<th><h2>Section</h2></th>\n')
	f.write('					<th><h2>PageName</h2></th>\n')
	f.write('					<th><h2>Link</h2></th>\n')
	f.write('				</tr>\n')
	f.write('			</thead>\n')
	f.write('			<tbody>\n')
	
	#以文件夹分章节
	for section in sections:
		if(len(section)==0):
			continue
			
		#写入第一列，跨行
		path, file = os.path.split(section[0])
		if(path.startswith('./')):
			path = path.split('./')[1]
		if(path.startswith('.\\')):
			path = path.split('.\\')[1]
		#print(path, file)
		f.write('				<tr>\n')
		f.write('					<td rowspan="'+str(len(section))+'"><h3>'+path+'</h3></td>\n')
		f.write('					<td>'+os.path.split(section[0])[1].replace('.html','')+'</td>\n')
		f.write('					<td><a href="'+ section[0] +'">' + section[0] + '</a></td>\n')
		f.write('				</tr>\n')
		#写入剩下的单元格
		for index in range(1, len(section)):
			page = section[index]
			f.write('				<tr>\n')
			f.write('					<td>'+os.path.split(page)[1].replace('.html','')+'</td>\n')
			f.write('					<td><a href="'+ page +'" target="_blank">' + page + '</a></td>\n')
			f.write('				</tr>\n')
		f.write('				</tr>\n')
		
	#html文件结尾
	f.write('			</tbody>\n')
	f.write('		</table>\n')
	f.write('	</div>\n')
	f.write('</body>\n')
	f.write('</html>')

print("生成"+des_filename+"完毕: "+os.path.join(root,des_filename))