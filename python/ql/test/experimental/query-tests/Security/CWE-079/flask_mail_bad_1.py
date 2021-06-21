# This tests that the user doesn't pass user-tainted data into the msg.html attribute.
# source: https://pythonhosted.org/Flask-Mail/
from flask_mail import Message

@app.route("/")
def index():

    msg = Message("Hello",
                  sender="from@example.com",
                  recipients=["to@example.com"])

    msg.html = "<b>testing</b>"

    mail.send(msg)
