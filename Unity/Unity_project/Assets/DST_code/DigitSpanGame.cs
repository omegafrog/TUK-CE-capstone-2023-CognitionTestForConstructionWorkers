using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;



/// <summary>
/// 게임 결과 변수 : isisDigitGamePassed , bool 자료형
/// </summary>



public class DigitSpanGame : MonoBehaviour
{
    private GameObject DST_GamestartPannel;//태그로 바꿔야됨
    private Button DST_Gamestartbutton;

    private string enteredNumber;


    private int wrongcnt;

    public static bool isDigitGamePassed;
    public static bool DigitGameresult;


    private TextMesh QuestionNumbers; //문제를 유니티 화면에 표시해줄 텍스트를 저장할 TextMesh 컴포넌트


    private int MaxRoundcnt;
    private int StraightRoundcnt;//정방향 라운드 갯수
    private int roundcnt;
    private bool isRoundPassed;
    private bool isQuestionDisplayActivated;

    private TextMesh InputNumbers; // 사용자가 입력한 숫자를 유니티 화면에 표시해줄 텍스트를 저장할 TextMesh 컴포넌트

    List<List<string>> Questionlistarray = new List<List<string>>
    {
        new List<string> {"2342","34519","607815","785","946"},
        new List<string> {"2951","53014","243867","458","769"},
        new List<string> {"4719","51246","362508","769","458"},
        new List<string> {"7453","12654","180923","694","758"},
        new List<string> {"2429","41303","785651","836","549"},
        new List<string> {"2635","54389","127104","574","869"},
        new List<string> {"9375","13158","424620","758","694"},
        new List<string> {"9250","24354","756128","795","486"},
        new List<string> {"1602","27358","419534","579","846"},
        new List<string> {"9243","25031","514836","586","947"},
        new List<string> {"6341","52347","942580","496","587"},
        new List<string> {"6502","25814","194373","495","786"},
        new List<string> {"4137","38920","512641","869","754"},
        new List<string> {"3731","52648","419250","748","956"},
        new List<string> {"5464","73921","503821","879","614"},
        new List<string> {"4765","10482","263491","865","947"},
        new List<string> {"4175","12930","836524","569","874"},
        new List<string> {"1563","53249","417802","748","596"},
        new List<string> {"5692","13123","574280","864","795"},
        new List<string> {"1245","39845","310627","986","475"},
        new List<string> {"5107","84924","231536","457","968"},
        new List<string> {"2965","75231","401438","875","694"},
        new List<string> {"1484","31562","796320","984","675"},
        new List<string> {"2076","58941","435324","956","487"},
        new List<string> {"4833","51657","492190","786","495"},
        new List<string> {"9205","54823","674361","597","468"},
        new List<string> {"3415","38024","276195","854","976"},
        new List<string> {"2859","23905","147614","548","796"},
        new List<string> {"4658","51391","409273","789","645"},
        new List<string> {"7141","80243","952653","698","754"}

    };

    List<string> Questionlist;

    System.Random random = new System.Random();

    private TextMesh SnRText;

    private bool isDSTstarted;


    void Start()
    {
        isDSTstarted = false;

        int randomIndex = random.Next(Questionlistarray.Count);


        DST_Gamestartbutton = GameObject.FindGameObjectWithTag("DST_Gamestartbutton").GetComponent<Button>();
        DST_GamestartPannel = GameObject.FindGameObjectWithTag("DST_GamestartPannel");
        //DST_PracticeStartbutton = GameObject.FindGameObjectWithTag("DST_PracticeButton").GetComponent<Button>();

        enteredNumber = "";
        Questionlist = Questionlistarray[randomIndex];
        wrongcnt = 0;
        roundcnt = 0;
        MaxRoundcnt = Questionlist.Count - 1;//인덱스라서 하나 빠져야 함
        StraightRoundcnt = 2;//0~2 인덱스까지 3개 라운드
        isRoundPassed = true;
        isQuestionDisplayActivated = true;
        isDigitGamePassed = false;
        DigitGameresult = false;

        QuestionNumbers = GameObject.FindGameObjectWithTag("DisplayQuestionText").GetComponent<TextMesh>();//이 태그를 쓴 3D 오브젝트에 글자를 보여줌
        InputNumbers = GameObject.FindGameObjectWithTag("DisplayUserInput").GetComponent<TextMesh>();//이 태그를 쓴 3D 오브젝트에 글자를 보여줌
        QuestionNumbers.text = "";

        SnRText = GameObject.FindGameObjectWithTag("DST_SnRText").GetComponent<TextMesh>();


    }



    // Update is called once per frame
    void Update()
    {
        if (isDSTstarted) ButtonClick();
        DST_Gamestartbutton.onClick.AddListener(GameStartButtonClick);
    }


    private void GameStartButtonClick()//UI 부분
    {
        isDSTstarted = true;
        DST_Gamestartbutton.gameObject.SetActive(false);
        DST_GamestartPannel.SetActive(false);
        StartCoroutine(GameStart(Questionlist));
    }


    public void ButtonClick()//버튼 클릭할 수 있게 하는 함수
    {
        if (Input.GetMouseButtonDown(0)) // 마우스 왼쪽 버튼 클릭 시
        {
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;

            if (Physics.Raycast(ray, out hit))
            {
                if (hit.collider.CompareTag("DST_InputButton")) //이 이름의 태그를 가진 버튼 오브젝트를 눌렀을 때
                {
                    int buttonValue = int.Parse(hit.collider.gameObject.name); // 버튼의 이름을 숫자로 변환하여 버튼 값으로 사용
                    ButtonPressed(buttonValue);
                }
            }
        }
    }



    private void ButtonPressed(int buttonValue)//0-9,입력,삭제 버튼 동작하게 하는 함수
    {
        if (buttonValue >= 0 && buttonValue <= 9)
        {
            enteredNumber += buttonValue.ToString(); // 입력한 버튼 값을 비밀번호에 추가
            ChangeInputText(enteredNumber);//비밀번호 표시를 누른 숫자 누적으로 업데이트
            InputNumbers.text = enteredNumber;
        }
        else if (buttonValue == 10) // 전체 삭제 버튼
        {
            enteredNumber = ""; // 비밀번호 초기화
            InputNumbers.text = enteredNumber;

        }
        else if (buttonValue == 11) // 입력 버튼
        {
            //연습이랑 실제 게임 구분 필요
            IsCorrectCheck();
        }

    }
    private void IsCorrectCheck()
    {

        if ((roundcnt > StraightRoundcnt) && wrongcnt == 0)//인덱스 4,5 일 때 역방향 입력 구현을 위함
        {
            Questionlist[roundcnt] = ReverseString(Questionlist[roundcnt]);
        }
        // 입력한 숫자 확인 및 처리 로직을 구현
        if (enteredNumber == Questionlist[roundcnt])
        {
            wrongcnt = 0;
            InputNumbers.text = "정답";
            enteredNumber = "";
            roundcnt++;


            isRoundPassed = true;
            isQuestionDisplayActivated = true;
            if (roundcnt == Questionlist.Count)//여기서는 숫자가 하나 늘어나니까 원본 Count를 사용
            {
                isDigitGamePassed = true;
                DigitGameresult = true;
                SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
            }
        }
        else
        {
            wrongcnt++;
            if (wrongcnt == 1)
            {
                InputNumbers.text = "다시 시도";
                enteredNumber = "";
            }
            else
            {
                InputNumbers.text = "실패";
                isRoundPassed = false;
                roundcnt = -1;
                isQuestionDisplayActivated = false;
                isDigitGamePassed = false;
                DigitGameresult = true;
                SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
            }

        }


    }




    string ReverseString(string input)//문자열 역순으로 바꿔줌
    {
        char[] charArray = input.ToCharArray();
        Array.Reverse(charArray);
        return new string(charArray);
    }

    private void ChangeInputText(string newText)//입력란의 글자를 바꾸기 위한 함수
    {
        InputNumbers.text = newText;//Textmash에 문자열을 넣는 것
    }


    IEnumerator GameStart(List<string> question)
    {


        for (; roundcnt <= Questionlist.Count && isRoundPassed == true;)
        {
            if (isQuestionDisplayActivated == true)
            {
                if (roundcnt > StraightRoundcnt)
                {
                    SnRText.text = "역방향 입력 : ";
                }
                else
                {
                    SnRText.text = "정방향 입력 : ";
                }
                foreach (char c in question[roundcnt])//인덱스 n번째 시퀀스를 하나씩 불러옴
                {
                    QuestionNumbers.text = c.ToString();
                    yield return new WaitForSeconds(1.0f); // 1초 동안 문제 보여줌
                }
                QuestionNumbers.text = "";
                InputNumbers.text = "";

                isQuestionDisplayActivated = false;//문제 표시하는 숫자가 안나오도록 함
            }
            yield return null;
        }
    }

}
