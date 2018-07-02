# coding=utf-8
import unittest
import re
from time import sleep
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import NoAlertPresentException
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support import expected_conditions as econd
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys

import apiritif
import selenium_taurus_extras

_vars = {}
_tpl = selenium_taurus_extras.Template(_vars)

class TestRequests(unittest.TestCase):
    def setUp(self):
        options = webdriver.ChromeOptions()
        self.driver = webdriver.Chrome(service_log_path='/Users/luis/Documents/Abstracta/GitHub/examples/taurus/selenium/edit-content/2018-07-02_17-19-41.067714/webdriver.log', chrome_options=options)
        self.driver.implicitly_wait(60.0)
        self.wnd_mng = selenium_taurus_extras.WindowManager(self.driver)
        self.frm_mng = selenium_taurus_extras.FrameManager(self.driver)

    def tearDown(self):
        self.driver.quit()

    def test_requests(self):
        self.driver.implicitly_wait(60.0)

        with apiritif.transaction_logged(u'Test'):
            self.driver.get(_tpl.apply(u'https://abstracta.github.io/examples/resources/edit-content/edit-content.html'))
            self.frm_mng.switch(u'index=0')
            self.driver.find_element(By.CSS_SELECTOR, _tpl.apply(u'body')).click()
            if self.driver.find_element(By.ID, 'tinymce').get_attribute('contenteditable'):
                self.driver.execute_script(
                    'arguments[0].innerHTML = %s;' % _tpl.str_repr(_tpl.apply(u'<p>test 9999<br></p>')),
                    self.driver.find_element(By.ID, 'tinymce')
                )
            else:
                raise NoSuchElementException("The element (By.ID, 'tinymce') is not contenteditable element")
            self.assertEqual(_tpl.apply(self.driver.find_element(By.CSS_SELECTOR, _tpl.apply('p')).get_attribute('innerText')).strip(), _tpl.apply(u'test 9999').strip())
            if self.driver.find_element(By.CSS_SELECTOR, '#tinymce').get_attribute('contenteditable'):
                self.driver.execute_script(
                    'arguments[0].innerHTML = %s;' % _tpl.str_repr(_tpl.apply(u'<p>test 99<br></p>')),
                    self.driver.find_element(By.CSS_SELECTOR, '#tinymce')
                )
            else:
                raise NoSuchElementException("The element (By.CSS_SELECTOR, '#tinymce') is not contenteditable element")
            self.assertEqual(_tpl.apply(self.driver.find_element(By.CSS_SELECTOR, _tpl.apply('p')).get_attribute('innerText')).strip(), _tpl.apply(u'test 99').strip())
            if self.driver.find_element(By.XPATH, "//body[@id='tinymce']").get_attribute('contenteditable'):
                self.driver.execute_script(
                    'arguments[0].innerHTML = %s;' % _tpl.str_repr(_tpl.apply(u'<p>test 0<br></p>')),
                    self.driver.find_element(By.XPATH, "//body[@id='tinymce']")
                )
            else:
                raise NoSuchElementException('The element (By.XPATH, "//body[@id=\'tinymce\']") is not contenteditable element')
            self.assertEqual(_tpl.apply(self.driver.find_element(By.CSS_SELECTOR, _tpl.apply('p')).get_attribute('innerText')).strip(), _tpl.apply(u'test 0').strip())
            self.frm_mng.switch(u'relative=parent')
            self.driver.find_element(By.CSS_SELECTOR, _tpl.apply(u'body')).click()


