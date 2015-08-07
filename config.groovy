environments {
    dev {
        jdbc {
            url = 'development'
            user = 'dev'
            password = '123'
        }
    }

    test {
        jdbc {
            url = 'test'
            user = 'tes'
            password = '456'
        }
    }
    pro {
        jdbc {
            url = 'production'
            user = 'pro'
            password = '789'
        }
    }
}
