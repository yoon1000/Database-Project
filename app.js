var mysql = require('mysql');
var express = require('express');
var app = express();

app.use(express.json())

var connection = mysql.createConnection({
    host: 'database-project.cg6nwrq9sktk.ap-northeast-2.rds.amazonaws.com',
    user: 'njs10919',
    password: 'yjhjy10919',
    database: 'example'
});

app.get('/',function(req, res){
    var message = '테스트 중입니다.';
    console.log('get test!')
    res.json({
        "result" : message
    });
});

app.get('/customer',function(req, res){
    var message = '데이터베이스 테스트 중입니다.';
    console.log('get test!')

    var sql = 'SELECT * FROM Customers';
    connection.query(sql, function(err, result, fields){
        if(err){
            console.log(err);
        }   
        else{
            console.log(result);
            res.json({
                'result' : result
            });
        }
    });
    
});
//영화 특정 키워드 검색
app.post('/user/movieKeyword', function (req, res) {
    var keyword = req.body.keyword;
    // 삽입을 수행하는 sql문.
    var sql = 'SELECT MovieName, MovieType FROM example.Movies WHERE MovieName = ? ';

    connection.query(sql, [keyword], function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            res.json({
                'result' : result
            });
            
        }
    });
});
//영화 리스트 가져오기-오름차순
app.get('/movie/ascending',function(req, res){
    var message = '데이터베이스 테스트 중입니다.';
    console.log('get test!')

    var sql = 'SELECT MovieName, MovieType FROM example.Movies ORDER BY MovieName';
    connection.query(sql, function(err, result, fields){
        if(err){
            console.log(err);
        }   
        else{
            console.log(result);
            res.json({
                'result' : result
            });
        }
    });
    
});
//영화 리스트 가져오기-내림차순
app.get('/movie/descending',function(req, res){
    var message = '데이터베이스 테스트 중입니다.';
    console.log('get test!')

    var sql = 'SELECT MovieName, MovieType FROM example.Movies ORDER BY MovieName DESC';
    connection.query(sql, function(err, result, fields){
        if(err){
            console.log(err);
        }   
        else{
            console.log(result);
            res.json({
                'result' : result
            });
        }
    });
    
});
//영화 리스트 가져오기-장르순 Action
app.get('/movie/genre/Action',function(req, res){
    var message = '데이터베이스 테스트 중입니다.';
    console.log('get test!')

    var sql = 'SELECT MovieName, MovieType FROM example.Movies WHERE MovieType = "Action" ORDER BY MovieType';
    connection.query(sql, function(err, result, fields){
        if(err){
            console.log(err);
        }   
        else{
            console.log(result);
            res.json({
                'result' : result
            });
        }
    });
    
});
//영화 리스트 가져오기-장르순 Drama
app.get('/movie/genre/Drama',function(req, res){
    var message = '데이터베이스 테스트 중입니다.';
    console.log('get test!')

    var sql = 'SELECT MovieName, MovieType FROM example.Movies WHERE MovieType = "Drama" ORDER BY MovieType';
    connection.query(sql, function(err, result, fields){
        if(err){
            console.log(err);
        }   
        else{
            console.log(result);
            res.json({
                'result' : result
            });
        }
    });
    
});
//영화 리스트 가져오기-장르순 Comedy
app.get('/movie/genre/Comedy',function(req, res){
    var message = '데이터베이스 테스트 중입니다.';
    console.log('get test!')

    var sql = 'SELECT MovieName, MovieType FROM example.Movies WHERE MovieType = "Comedy" ORDER BY MovieType';
    connection.query(sql, function(err, result, fields){
        if(err){
            console.log(err);
        }   
        else{
            console.log(result);
            res.json({
                'result' : result
            });
        }
    });
    
});
//영화 리스트 가져오기-장르순 Romance
app.get('/movie/genre/Romance',function(req, res){
    var message = '데이터베이스 테스트 중입니다.';
    console.log('get test!')

    var sql = 'SELECT MovieName, MovieType FROM example.Movies WHERE MovieType = "Romance" ORDER BY MovieType';
    connection.query(sql, function(err, result, fields){
        if(err){
            console.log(err);
        }   
        else{
            console.log(result);
            res.json({
                'result' : result
            });
        }
    });
    
});
//사용자 로그인 '/user/login'
app.post('/user/login', function (req, res) {
    //console.log(req.body);
    var userId = req.body.id;
    var userPw = req.body.password;
    var sql = 'SELECT * from Customers WHERE CustomerId = ?';

    connection.query(sql, [userId], function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            //console.log(err);
        } else {
            console.log(result);
            if (result.length === 0) {
                resultCode = 204;
                message = '존재하지 않는 계정입니다!';
            } else if (userPw !== result[0].Password) {
                resultCode = 204;
                message = '비밀번호가 틀렸습니다!';
            } else {
                resultCode = 200;
                message = 'Login! Welcome, ' + result[0].Fname;
            }
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    })
});
//사용자 등록 '/user/join'
app.post('/user/join', function (req, res) {
    console.log(req.body);
    var userId = req.body.id;
    var userFname = req.body.Fname;
    var userLname = req.body.Lname;
    var userEmail = req.body.email;
    var userPw = req.body.password;
    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO Customers (CustomerId, Fname, Lname, Email, Password) VALUES (?, ?, ?, ?, ?)';
    var params = [userId, userFname, userLname,userEmail, userPw];

    // sql 문의 ?는 두번째 매개변수로 넘겨진 params의 값으로 치환된다.
    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = '회원가입에 성공했습니다.';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});
//사용자의 account Type 가져오기
app.post('/user/accountType', function (req, res) {
    var userId = req.body.id;
    // 삽입을 수행하는 sql문.
    var sql = 'SELECT AccountType FROM Accounts WHERE CustomerId = ?';

    connection.query(sql, [userId], function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';
        var accountType = '';

        if (err) {
            console.log(err);
        } else {
            console.log(result);
            if (result.length === 0) {
                resultCode = 204;
                message = 'You have to select your account type.';
            } else {
                resultCode = 200;
                message = userId + ' is an ' + result[0].AccountType + ' user.' ;
                accountType = result[0].AccountType;
            }
        }

        res.json({
            'code': resultCode,
            'message': message,
            'accountType' : accountType
        });
    });
});
//사용자의 계정정보 등록하기
app.post('/user/accountType/plan', function (req, res) {
    console.log(req.body);
    var userAc = req.body.account;
    var userId = req.body.id;
    var date = req.body.date;
    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO Accounts (AccountType, CustomerId, AccCreateDate) VALUES (?, ?, ?)';
    var params = [userAc, userId, date];

    // sql 문의 ?는 두번째 매개변수로 넘겨진 params의 값으로 치환된다.
    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = '계정 정보 등록에 성공하였습니다';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});
//사용자의 계정 정보 가져오기
app.post('/user/account', function (req, res) {
    var userId = req.body.id;
    // 삽입을 수행하는 sql문.
    var sql = 'SELECT C.Fname, C.Lname, C.CustomerId, C.Email, A.AccountType FROM Customers AS C, Accounts AS A WHERE C.CustomerId = A.CustomerId AND C.CustomerId = ?';

    connection.query(sql, [userId], function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            console.log(result);
            res.json({
                'result' : result
            });
        }
    });
});
//사용자의 영화 큐 가져오기
app.post('/user/movieQueue', function (req, res) {
    var userId = req.body.id;
    // 삽입을 수행하는 sql문.
    var sql = 'SELECT M.MovieName, M.MovieType FROM example.Movies AS M, example.MovieQueue AS Q WHERE   M.MovieName = Q.MovieName AND Q.CustomerId = ? ORDER BY Q.No';

    connection.query(sql, [userId], function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            if (result.length === 0) {
                resultCode = 204;
                message = 'You have to add movie in your movie queue.';
            } else {
                resultCode = 200;
                console.log(result);
                res.json({
                    'code' : resultCode,
                    'message' : message,
                    'result' : result
                });
            }
        }
    });
});
//사용자의 영화 큐에 영화 추가하기
app.post('/user/addMovie', function (req, res) {
    console.log(req.body);
    var userId = req.body.id;
    var movieName = req.body.movieName;
    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO MovieQueue (CustomerId, MovieName) VALUES (?, ?)';
    var params = [userId, movieName];

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = '계정 정보 등록에 성공하였습니다';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});
//사용자의 영화 큐의 맨 처음 영화 order에 추가하기 + 삭제하기
app.post('/user/OrderFull', function (req, res) {
    console.log(req.body);
    var userId = req.body.id;

    // 삽입을 수행하는 sql문.
    var sql = 'SELECT COUNT(MovieName) FROM example.Order WHERE CustomerID = ?';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            res.json({
                'result': result,
            });
        }

    });
});
app.post('/user/FirstMovie', function (req, res) {
    console.log(req.body);
    var userId = req.body.id;

    // 삽입을 수행하는 sql문.
    var sql = 'SELECT MovieName FROM MovieQueue WHERE CustomerID = ? LIMIT 1';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            res.json({
                'result': result,
            });
        }

    });
});
app.post('/user/addOrder', function (req, res) {
    console.log(req.body);
    var userId = req.body.id;
    var movieName = req.body.movieName;
    var rentalDate = req.body.rentalDate;
    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO example.Order (MovieName, CustomerId, RentalDate) VALUES (?, ?, ?)';
    var params = [movieName, userId, rentalDate];

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            res.json({
                'result': result
            });
        }

        
    });
});
app.post('/user/deleteQueue', function (req, res) {
    console.log(req.body);
    var userId = req.body.id;
    // 삽입을 수행하는 sql문.
    var sql = 'DELETE FROM MovieQueue WHERE CustomerId = ? LIMIT 1';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = '계정 정보 등록에 성공하였습니다';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});

//사용지의 오더 내용 가져오기
app.post('/user/myOrder', function (req, res) {
    var userId = req.body.id;
    // 삽입을 수행하는 sql문.
    var sql = 'SELECT M.MovieName, M.MovieType FROM example.Movies AS M, example.Order AS Q WHERE   M.MovieName = Q.MovieName AND Q.CustomerId = ? ORDER BY Q.OrderId';

    connection.query(sql, [userId], function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            if (result.length === 0) {
                resultCode = 204;
                message = 'You have to add movie in your movie queue.';
            } else {
                resultCode = 200;
                console.log(result);
                res.json({
                    'code' : resultCode,
                    'message' : message,
                    'result' : result
                });
            }
        }
    });
});

app.listen(3000, function(){
    console.log('Connected 3000 port!');
});
