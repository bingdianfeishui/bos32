#encoding:UTF-8
from bs4 import BeautifulSoup

import re

f=open('d:/laoma/2.html',encoding='utf-8')
soup = BeautifulSoup(f,'html.parser')

for p in soup.find(string=re.compile("未完待续")).parent.find_next_siblings():
	p.extract()
	
	
from bs4 import BeautifulSoup
from urllib.request import urlopen
import re

# 去除标题中的非法字符 (Windows)
def validateTitle(title):
    rstr = r"[\/\\\:\*\?\"\<\>\|]"  # '/\:*?"<>|'
    new_title = re.sub(rstr, "", title)
    return new_title

url = "http://www.cnblogs.com/swiftma/p/5631311.html"
html = urlopen(url)
#html = html.decode('UTF-8')
#获取通用框架
frame = BeautifulSoup(html, "html.parser")

#处理静态资源
heads = frame.find_all(name="link")
for link in heads:
    if link.get('href').startswith('/'):
        link.attrs['href'] = "http://www.cnblogs.com"+link.get('href')
scripts = frame.find_all(name="script")
for scr in scripts:
    if not scr.get('src'):
        continue
    if scr.get('src').startswith('//common'):
        scr.attrs['src'] = "http:"+scr.get('src')
    if scr.get('src').startswith('/bundles'):
        scr.attrs['src'] = "http://www.cnblogs.com"+scr.get('src')

#print(frame)

#获取文章链接
lists = frame.find_all(name='a',attrs={'href':re.compile(r'http://www.cnblogs.com/swiftma/p/\d{1,10}.html')})

links = []
for a in lists:
    links.append(a.get('href'))
    #href = a.get('href')
    #matchObj = re.match(r'http://www.cnblogs.com/swiftma/p/(\d{1,10}).html', href)
    #if matchObj:
    #    links.append(matchObj.group(1))

#print(links)
for l in links:
    html = urlopen(l)
    soup = BeautifulSoup(html, 'html.parser')
    #文章主体div
    post = soup.find(name='div',attrs={'class':'post'})
    
    #去除图片
    post.find(name='img').parent.clear()
    
    #文章标题
    tittle=soup.find(name='a',attrs={'id':'cb_post_title_url'}).get_text()
    #print(tittle)
    
    #清空frame的body内容
    frame.body.clear()
    #拼接html
    frame.body.append(post)
    #print(frame)
    
    #文件名
    file_name = 'd:/laoma/' + validateTitle(tittle) + '.html'
    with open(file_name, 'w',encoding='utf-8') as file:
        try:
            file.write(frame.prettify())
            print("Done: ", file_name)
        except e:
            print("Error: ", file_name,"  ",e)
            file.flush()
        finally:
            file.close()


